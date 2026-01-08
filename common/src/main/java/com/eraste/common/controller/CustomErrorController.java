package com.eraste.common.controller;

import com.eraste.common.response.ApiResponse;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Custom error controller replacing the default Whitelabel Error Page.
 * <p>
 * This controller handles all errors and returns a consistent JSON response
 * using the {@link ApiResponse} format. It can be reused across all microservices.
 * </p>
 *
 * @author Eraste
 * @version 1.0.0
 * @since 1.0.0
 */
@RestController
public class CustomErrorController implements ErrorController {

    /**
     * Handles all error requests and returns a JSON response.
     * <p>
     * Maps HTTP status codes to appropriate error messages:
     * <ul>
     *   <li>404 - Resource not found</li>
     *   <li>403 - Access denied</li>
     *   <li>401 - Unauthorized</li>
     *   <li>400 - Bad request</li>
     *   <li>500 - Internal server error</li>
     * </ul>
     * </p>
     *
     * @param request the HTTP request containing error information
     * @return ResponseEntity with ApiResponse containing error details
     */
    @RequestMapping("/error")
    public ResponseEntity<ApiResponse<Void>> handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object errorMessage = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        String path = (String) request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);

        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        String message = "An unexpected error occurred";

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            httpStatus = HttpStatus.valueOf(statusCode);

            switch (statusCode) {
                case 404:
                    message = "The requested resource was not found";
                    break;
                case 403:
                    message = "Access denied";
                    break;
                case 401:
                    message = "Unauthorized access";
                    break;
                case 400:
                    message = "Bad request";
                    break;
                case 405:
                    message = "Method not allowed";
                    break;
                case 500:
                    message = "Internal server error";
                    break;
                default:
                    message = errorMessage != null && !errorMessage.toString().isEmpty()
                            ? errorMessage.toString()
                            : "Error: " + httpStatus.getReasonPhrase();
            }
        }

        ApiResponse<Void> response = ApiResponse.<Void>error(message)
                .withPath(path != null ? path : request.getRequestURI());

        return ResponseEntity.status(httpStatus).body(response);
    }
}
