package com.rich.budgetapi.api.openapi.controlller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.rich.budgetapi.api.exceptionhandler.ProblemApi;
import com.rich.budgetapi.api.model.PermissionModel;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Profiles")
public interface ProfilePermissionControllerOpenApi {

	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Success request"),

			@ApiResponse(responseCode = "404", description = "Profile not found", content = @Content(schema = @Schema(implementation = ProblemApi.class)))
	})
	@Operation(summary = "List all permissions for a profile")
	public ResponseEntity<List<PermissionModel>> toList(Long profileId);

	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Success request"),

			@ApiResponse(responseCode = "404", description = "Profile not found", content = @Content(schema = @Schema(implementation = ProblemApi.class)))
	})
	@Operation(summary = "Find a permission from a profile by PROFILE ID and PERMISSION ID")
	public ResponseEntity<PermissionModel> toFind(Long profileId, Long permissionId);

	@ApiResponses({
			@ApiResponse(responseCode = "204", description = "No content"),

			@ApiResponse(responseCode = "404", description = "Profile and/or Permission not found")
	})
	@Operation(summary = "Associate a profile with a user by PROFILE ID and PERMISSION ID")
	public ResponseEntity<Void> toConnect(Long profileId, Long permissionId);

	@ApiResponses({
			@ApiResponse(responseCode = "204", description = "No content"),

			@ApiResponse(responseCode = "404", description = "Profile and/or Permission not found")
	})
	@Operation(summary = "Disassociate a profile from a user by PROFILE ID and PERMISSION ID")
	public ResponseEntity<Void> toDisassociate(Long profileId, Long permissionId);
}
