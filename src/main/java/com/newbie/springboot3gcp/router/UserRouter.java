package com.newbie.springboot3gcp.router;

import com.newbie.springboot3gcp.entity.User;
import com.newbie.springboot3gcp.handler.UserHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class UserRouter {

    private final UserHandler handler;

    public UserRouter(UserHandler handler) {
        this.handler = handler;
    }

    @RouterOperations({
            @RouterOperation(
                    path = "/api/users/v1/find/{id}",
                    beanClass = UserHandler.class,
                    beanMethod = "findById",
                    method = RequestMethod.GET,
                    operation = @Operation(
                            description = "Find user By Id",
                            operationId = "findUser",
                            tags = "User",
                            parameters = {@Parameter(in = ParameterIn.PATH, name = "id", description = "User Id", required = true)},
                            responses = @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = User.class)))
                    )
            ),
            @RouterOperation(
                    path = "/api/users/v1/all",
                    beanClass = UserHandler.class,
                    beanMethod = "findAll",
                    method = RequestMethod.GET,
                    operation = @Operation(
                            description = "Get all users",
                            operationId = "getUsers",
                            tags = "User",
                            responses = @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = User.class)))
                    )
            ),
            @RouterOperation(
                    path = "/api/users/v1/country/{country}",
                    beanClass = UserHandler.class,
                    beanMethod = "findByCountry",
                    method = RequestMethod.GET,
                    operation = @Operation(
                            description = "Find users by country",
                            operationId = "findUserByCountry",
                            tags = "User",
                            parameters = {@Parameter(in = ParameterIn.PATH, name = "country", description = "Country code", required = true)},
                            responses = @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = User.class)))
                    )
            ),
            @RouterOperation(
                    path = "/api/users/v1/update/{id}",
                    beanClass = UserHandler.class,
                    beanMethod = "update",
                    method = RequestMethod.PUT,
                    operation = @Operation(
                            description = "Update user",
                            operationId = "updateUser",
                            tags = "User",
                            parameters = {@Parameter(in = ParameterIn.PATH, name = "id", description = "User Id", required = true)},
                            requestBody = @RequestBody(
                                    description = "Details of the User to be updated",
                                    required = true,
                                    content = @Content(
                                            schema = @Schema(implementation = User.class),
                                            mediaType = MediaType.APPLICATION_JSON_VALUE
                                    )),
                            responses = @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = User.class)))
                    )
            ),
            @RouterOperation(
                    path = "/api/users/v1/delete/{id}",
                    beanClass = UserHandler.class,
                    beanMethod = "delete",
                    method = RequestMethod.DELETE,
                    operation = @Operation(
                            description = "delete user",
                            operationId = "deleteUser",
                            tags = "User",
                            parameters = {@Parameter(in = ParameterIn.PATH, name = "id", description = "User Id", required = true)},
                            responses = @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = User.class)))
                    )
            ),
            @RouterOperation(
                    path = "/api/users/v1/create",
                    beanClass = UserHandler.class,
                    beanMethod = "save",
                    method = RequestMethod.POST,
                    operation = @Operation(
                            description = "create user",
                            operationId = "createUser",
                            tags = "User",
                            requestBody = @RequestBody(
                                    description = "Details of the User to be created",
                                    required = true,
                                    content = @Content(
                                            schema = @Schema(implementation = User.class),
                                            mediaType = MediaType.APPLICATION_JSON_VALUE
                                    )),
                            responses = @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = User.class)))
                    )
            )
    })
    @Bean
    public RouterFunction<ServerResponse> userRoute() {
        return RouterFunctions.route()
                .path("/api/users", builder -> builder
                        .GET("/v1/find/{id}", handler::findById)
                        .GET("/v1/all", handler::findAll)
                        .GET("/v1/country/{country}", handler::findByCountry)
                        .PUT("/v1/update/{id}", accept(APPLICATION_JSON), handler::update)
                        .DELETE("/v1/delete/{id}", handler::delete)
                        .POST("/v1/create", accept(APPLICATION_JSON), handler::save))
                .build();
    }

}
