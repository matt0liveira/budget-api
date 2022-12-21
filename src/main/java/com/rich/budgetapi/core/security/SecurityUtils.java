package com.rich.budgetapi.core.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public Long getUserIdAuthenticated() {
        Jwt jwt = (Jwt) getAuthentication().getPrincipal();

        return jwt.getClaim("user_id");
    }

    public boolean isAuthenticated() {
        return getAuthentication().isAuthenticated();
    }

    private boolean hasAuthority(String authorityName) {
        return getAuthentication()
                .getAuthorities()
                .stream()
                .anyMatch(authority -> authority.getAuthority().equals(authorityName));
    }

    public boolean hasScopeRead() {
        return hasAuthority("SCOPE_READ");
    }

    public boolean hasScopeWrite() {
        return hasAuthority("SCOPE_WRITE");
    }

    public boolean canConsultUsersProfilesPermissions() {
        return hasScopeRead() && hasAuthority("CONSULT_USERS_PROFILES_PERMISSIONS");
    }

    public boolean canConsultUser(Long userId) {
        return canConsultUsersProfilesPermissions()
                || hasScopeRead() && userAuthenticatedEquals(userId);
    }

    public boolean canChangeUsersProfilesPermissions() {
        return hasScopeWrite() && hasAuthority("CHANGE_USERS_PROFILES_PERMISSIONS");
    }

    public boolean canChangeUser(Long userId) {
        return canChangeUsersProfilesPermissions() || hasScopeWrite() && userAuthenticatedEquals(userId);
    }

    public boolean canChangePassword(Long userId) {
        return hasScopeWrite() && userAuthenticatedEquals(userId);
    }

    public boolean canConsultCategories() {
        return hasScopeRead() && hasAuthority("CONSULT_CATEGORIES");
    }

    public boolean canFindCategory(Long userId) {
        return canConsultCategories() || userAuthenticatedEquals(userId);
    }

    public boolean canConsultCategoriesByUser(Long userId) {
        return canConsultCategories() || userAuthenticatedEquals(userId);
    }

    public boolean canSearchTransactions(Long userId) {
        return hasScopeRead() && (hasAuthority("CONSULT_TRANSATIONS")) || userAuthenticatedEquals(userId);
    }

    public boolean userAuthenticatedEquals(Long userId) {
        return getUserIdAuthenticated() != null && userId != null && getUserIdAuthenticated().equals(userId);
    }

    public boolean canUpdateTransactions(Long userId) {
        return hasScopeWrite() && (hasAuthority("CHANGE_TRANSACTIONS")) || userAuthenticatedEquals(userId);
    }

    public boolean canConsultReports(Long userId) {
        return hasScopeRead() && (hasAuthority("CONSULT_REPORTS")) || userAuthenticatedEquals(userId);
    }

}
