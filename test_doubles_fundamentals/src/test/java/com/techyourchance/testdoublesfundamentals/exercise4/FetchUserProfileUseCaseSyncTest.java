package com.techyourchance.testdoublesfundamentals.exercise4;

import com.techyourchance.testdoublesfundamentals.example4.networking.NetworkErrorException;
import com.techyourchance.testdoublesfundamentals.exercise4.FetchUserProfileUseCaseSync.UseCaseResult;
import com.techyourchance.testdoublesfundamentals.exercise4.networking.UserProfileHttpEndpointSync;
import com.techyourchance.testdoublesfundamentals.exercise4.users.User;
import com.techyourchance.testdoublesfundamentals.exercise4.users.UsersCache;

import org.jetbrains.annotations.Nullable;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class FetchUserProfileUseCaseSyncTest {

    private static final String USER_ID = "userId";
    private static final String USER_FULL_NAME = "My User";
    private static final String USER_IMAGE_URL = "My Image URL";

    FetchUserProfileUseCaseSync SUT;

    UserProfileHttpEndpointSyncTd mUserProfileHttpEndpointSyncTd;
    UsersCacheTd mUsersCacheTd;

    @Before
    public void setup() throws Exception {
        mUserProfileHttpEndpointSyncTd = new UserProfileHttpEndpointSyncTd();
        mUsersCacheTd = new UsersCacheTd();
        SUT = new FetchUserProfileUseCaseSync(mUserProfileHttpEndpointSyncTd, mUsersCacheTd);
    }

    @Test
    public void fetchUserProfileSync_success_userIdPassedToEndpoint() throws Exception {
        SUT.fetchUserProfileSync(USER_ID);
        assertThat(mUserProfileHttpEndpointSyncTd.mUserId, is(USER_ID));
    }

    @Test
    public void fetchUserProfileSync_success_userCached() throws Exception {
        SUT.fetchUserProfileSync(USER_ID);
        User cachedUser = mUsersCacheTd.getUser(USER_ID);
        assertThat(cachedUser.getUserId(), is(USER_ID));
        assertThat(cachedUser.getFullName(), is(USER_FULL_NAME));
        assertThat(cachedUser.getImageUrl(), is(USER_IMAGE_URL));
    }

    @Test
    public void fetchUserProfileSync_generalError_userNotCached() throws Exception {
        mUserProfileHttpEndpointSyncTd.mIsGeneralError = true;
        SUT.fetchUserProfileSync(USER_ID);
        Assert.assertThat(mUsersCacheTd.getUser(USER_ID), is(nullValue()));
    }

    @Test
    public void fetchUserProfileSync_authError_userNotCached() throws Exception {
        mUserProfileHttpEndpointSyncTd.mIsAuthError = true;
        SUT.fetchUserProfileSync(USER_ID);
        assertThat(mUsersCacheTd.getUser(USER_ID), is(nullValue()));
    }

    @Test
    public void fetchUserProfileSync_serverError_userNotCached() throws Exception {
        mUserProfileHttpEndpointSyncTd.mIsServerError = true;
        SUT.fetchUserProfileSync(USER_ID);
        assertThat(mUsersCacheTd.getUser(USER_ID), is(nullValue()));
    }

    @Test
    public void fetchUserProfileSync_success_successReturned() throws Exception {
        UseCaseResult result = SUT.fetchUserProfileSync(USER_ID);
        assertThat(result, is(UseCaseResult.SUCCESS));
    }

    @Test
    public void fetchUserProfileSync_serverError_failureReturned() throws Exception {
        mUserProfileHttpEndpointSyncTd.mIsServerError = true;
        UseCaseResult result = SUT.fetchUserProfileSync(USER_ID);
        assertThat(result, is(FetchUserProfileUseCaseSync.UseCaseResult.FAILURE));
    }

    @Test
    public void fetchUserProfileSync_authError_failureReturned() throws Exception {
        mUserProfileHttpEndpointSyncTd.mIsAuthError = true;
        UseCaseResult result = SUT.fetchUserProfileSync(USER_ID);
        assertThat(result, is(FetchUserProfileUseCaseSync.UseCaseResult.FAILURE));
    }

    @Test
    public void fetchUserProfileSync_generalError_failureReturned() throws Exception {
        mUserProfileHttpEndpointSyncTd.mIsGeneralError = true;
        UseCaseResult result = SUT.fetchUserProfileSync(USER_ID);
        assertThat(result, is(FetchUserProfileUseCaseSync.UseCaseResult.FAILURE));
    }

    @Test
    public void fetchUserProfileSync_networkError_networkErrorReturned() throws Exception {
        mUserProfileHttpEndpointSyncTd.mIsNetworkError = true;
        UseCaseResult result = SUT.fetchUserProfileSync(USER_ID);
        assertThat(result, is(FetchUserProfileUseCaseSync.UseCaseResult.NETWORK_ERROR));
    }

    // Helper classes
    private class UserProfileHttpEndpointSyncTd implements UserProfileHttpEndpointSync {
        public String mUserId = "";
        public boolean mIsGeneralError = false;
        public boolean mIsAuthError = false;
        public boolean mIsServerError = false;
        public boolean mIsNetworkError = false;


        @Override
        public EndpointResult getUserProfile(String userId) throws NetworkErrorException {
            mUserId = userId;
            if (mIsGeneralError) {
                return new EndpointResult(EndpointResultStatus.GENERAL_ERROR, "", "", "");
            } else if (mIsAuthError) {
                return new EndpointResult(EndpointResultStatus.AUTH_ERROR, "", "", "");
            }  else if (mIsServerError) {
                return new EndpointResult(EndpointResultStatus.SERVER_ERROR, "", "", "");
            } else if (mIsNetworkError) {
                throw new NetworkErrorException();
            } else {
                return new EndpointResult(EndpointResultStatus.SUCCESS, USER_ID, USER_FULL_NAME, USER_IMAGE_URL);
            }
        }
    }

    private class UsersCacheTd implements UsersCache{

        private List<User> mUsers = new ArrayList<>(1);

        @Override
        public void cacheUser(User user) {
            User existingUser = getUser(user.getUserId());
            if (existingUser != null) {
                mUsers.remove(existingUser);
            }
            mUsers.add(user);
        }

        @Override
        @Nullable
        public User getUser(String userId) {
            for (User user : mUsers) {
                if (user.getUserId().equals(userId)) {
                    return user;
                }
            }
            return null;
        }
    }
}