package com.rich.budgetapi.api.openapi.controlller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.rich.budgetapi.api.exceptionhandler.ProblemApi;
import com.rich.budgetapi.api.model.ProfileModel;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Users")
public interface UserProfileControllerOpenApi {

	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Success request"),

			@ApiResponse(responseCode = "404", description = "User and/or profile not found", content = @Content(schema = @Schema(implementation = ProblemApi.class)))
	})
	@Operation(summary = "List all profiles of an user by USER ID")
	public ResponseEntity<List<ProfileModel>> toList(Long userId);

	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Success request"),

			@ApiResponse(responseCode = "404", description = "User and/or profile not found", content = @Content(schema = @Schema(implementation = ProblemApi.class)))
	})
	@Operation(summary = "Find a profile of an user by USER ID and PROFILE ID")
	public ResponseEntity<ProfileModel> toFind(Long userId, Long profileId);

	@ApiResponses({
			@ApiResponse(responseCode = "204", description = "No content"),
			@ApiResponse(responseCode = "404", description = "User and/or profile not found")
	})
	@Operation(summary = "Associate a profile with a user by USER ID and PROFILE ID")
	public ResponseEntity<Void> connectProfile(Long userId, Long profileId);

	@ApiResponses({
			@ApiResponse(responseCode = "204", description = "No content"),
			@ApiResponse(responseCode = "404", description = "User and/or profile not found")
	})
	@Operation(summary = "Disassociate a profile from a user by USER ID and PROFILE ID")
	public ResponseEntity<Void> disassociateProfile(Long userId, Long profileId);
}