package com.rich.budgetapi.api.openapi.controlller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.rich.budgetapi.api.exceptionhandler.ProblemApi;
import com.rich.budgetapi.api.model.ProfileModel;
import com.rich.budgetapi.api.model.input.ProfileInputModel;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Profiles")
public interface ProfileControllerOpenApi {

	@Operation(summary = "List all profiles")
	public ResponseEntity<List<ProfileModel>> toList();

	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Success request"),

			@ApiResponse(responseCode = "404", description = "Profile not found", content = @Content(schema = @Schema(implementation = ProblemApi.class)))
	})
	@Operation(summary = "Find a profile by ID")
	public ResponseEntity<ProfileModel> toFind(Long profileId);

	@ApiResponse(responseCode = "201", description = "Created")
	@Operation(summary = "Add a new profile")
	public ResponseEntity<ProfileModel> toAdd(ProfileInputModel profileInput);

	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Success request"),

			@ApiResponse(responseCode = "404", description = "Profile not found", content = @Content(schema = @Schema(implementation = ProblemApi.class)))
	})
	@Operation(summary = "Update profile data by ID")
	public ResponseEntity<ProfileModel> toUpdate(Long profileId,
			ProfileInputModel profileInput);

	@ApiResponses({
			@ApiResponse(responseCode = "204", description = "No content"),

			@ApiResponse(responseCode = "404", description = "Profile not found")
	})
	@Operation(summary = "Remove a profile by ID")
	public ResponseEntity<Void> toRemove(Long profileId);
}
