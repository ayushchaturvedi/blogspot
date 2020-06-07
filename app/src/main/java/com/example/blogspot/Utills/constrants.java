package com.example.blogspot.Utills;

public interface constrants {
    String BASE_URL = "http://3.134.93.237:5000/api/v1/";
    String LOGIN_URL = BASE_URL + "user/login";
    String REGISTER_URL = BASE_URL + "user/register";
    String GET_PROFILE_URL = BASE_URL+"user/get-profile";
    String GET_ARTICLES_URL = BASE_URL+"article/get-articles";
    String GET_ARTICLES_POSTS = BASE_URL+"post/get-posts";
    String ADD_POST = BASE_URL+"post/add-post";
    String ADD_EDIT_ARTICLE = BASE_URL+"article/add-edit-article";
    String DELETE_ARTICLE = BASE_URL+"article/delete-article";
    String VIEW_POST_URL = BASE_URL+"post/view-post";
    String GET_COMMENTS_URL = BASE_URL+"comment/get-comments";
    String ADD_COMMENT_URL = BASE_URL+"comment/add-comment";
    String DELETE_POST = BASE_URL+"post/delete-post";
}
