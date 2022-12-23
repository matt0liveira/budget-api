package com.rich.budgetapi.api.openapi.controlller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.multipart.MultipartFile;

import com.rich.budgetapi.api.exceptionhandler.ProblemApi;
import com.rich.budgetapi.api.model.PhotoUserModel;
import com.rich.budgetapi.api.model.input.PhotoUserInputModel;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Users")
public interface UserPhotoControllerOpenApi {

	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Success request"),

			@ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ProblemApi.class)))
	})
	@Operation(summary = "Update user photo by USER ID")
	public ResponseEntity<PhotoUserModel> toUpdate(Long userId,
			PhotoUserInputModel photoUserInput, MultipartFile file) throws IOException;

	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Success request"),

			@ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ProblemApi.class)))
	})
	@Operation(summary = "Find user photo by USER ID")
	public ResponseEntity<?> toFind(Long userId,
			String acceptHeader) throws HttpMediaTypeNotAcceptableException;

	@Operation(summary = "Remove user photo by USER ID")
	@ApiResponses({
			@ApiResponse(responseCode = "204", description = "No content"),

			@ApiResponse(responseCode = "404", description = "User not found")
	})
	public ResponseEntity<Void> toRemove(Long userId);
}
