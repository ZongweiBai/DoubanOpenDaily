package com.monosky.daily.module;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * 每日文章
 */
public class ContentData {

    private int count;
    private int offset;
    private String date;
    private int total;

    private List<PostsEntity> posts;

    public void setCount(int count) {
        this.count = count;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setPosts(List<PostsEntity> posts) {
        this.posts = posts;
    }

    public int getCount() {
        return count;
    }

    public int getOffset() {
        return offset;
    }

    public String getDate() {
        return date;
    }

    public int getTotal() {
        return total;
    }

    public List<PostsEntity> getPosts() {
        return posts;
    }

    public static class PostsEntity {
        private int display_style;
        private boolean is_editor_choice;
        private String published_time;
        private String original_url;
        private String url;
        private String short_url;
        private boolean is_liked;

        private AuthorEntity author;
        private String column;
        private int app_css;
        @JSONField(name = "abstract")
        private String abstractX;
        private String date;
        private int like_count;
        private int comments_count;
        private String created_time;
        private String title;
        private String share_pic_url;
        private String type;
        private int id;
        private List<ThumbsEntity> thumbs;

        public void setDisplay_style(int display_style) {
            this.display_style = display_style;
        }

        public void setIs_editor_choice(boolean is_editor_choice) {
            this.is_editor_choice = is_editor_choice;
        }

        public void setPublished_time(String published_time) {
            this.published_time = published_time;
        }

        public void setOriginal_url(String original_url) {
            this.original_url = original_url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setShort_url(String short_url) {
            this.short_url = short_url;
        }

        public void setIs_liked(boolean is_liked) {
            this.is_liked = is_liked;
        }

        public void setAuthor(AuthorEntity author) {
            this.author = author;
        }

        public void setColumn(String column) {
            this.column = column;
        }

        public void setApp_css(int app_css) {
            this.app_css = app_css;
        }

        public void setAbstractX(String abstractX) {
            this.abstractX = abstractX;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public void setLike_count(int like_count) {
            this.like_count = like_count;
        }

        public void setComments_count(int comments_count) {
            this.comments_count = comments_count;
        }

        public void setCreated_time(String created_time) {
            this.created_time = created_time;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setShare_pic_url(String share_pic_url) {
            this.share_pic_url = share_pic_url;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setThumbs(List<ThumbsEntity> thumbs) {
            this.thumbs = thumbs;
        }

        public int getDisplay_style() {
            return display_style;
        }

        public boolean isIs_editor_choice() {
            return is_editor_choice;
        }

        public String getPublished_time() {
            return published_time;
        }

        public String getOriginal_url() {
            return original_url;
        }

        public String getUrl() {
            return url;
        }

        public String getShort_url() {
            return short_url;
        }

        public boolean isIs_liked() {
            return is_liked;
        }

        public AuthorEntity getAuthor() {
            return author;
        }

        public String getColumn() {
            return column;
        }

        public int getApp_css() {
            return app_css;
        }

        public String getAbstractX() {
            return abstractX;
        }

        public String getDate() {
            return date;
        }

        public int getLike_count() {
            return like_count;
        }

        public int getComments_count() {
            return comments_count;
        }

        public String getCreated_time() {
            return created_time;
        }

        public String getTitle() {
            return title;
        }

        public String getShare_pic_url() {
            return share_pic_url;
        }

        public String getType() {
            return type;
        }

        public int getId() {
            return id;
        }

        public List<ThumbsEntity> getThumbs() {
            return thumbs;
        }

        public static class AuthorEntity {
            private boolean is_followed;
            private String editor_notes;
            private String uid;
            private String resume;
            private String url;
            private String avatar;
            private String name;
            private boolean is_special_user;
            private String last_post_time;
            private int n_posts;
            private String alt;
            private String large_avatar;
            private String id;
            private boolean is_auth_author;

            public void setIs_followed(boolean is_followed) {
                this.is_followed = is_followed;
            }

            public void setEditor_notes(String editor_notes) {
                this.editor_notes = editor_notes;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public void setResume(String resume) {
                this.resume = resume;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setIs_special_user(boolean is_special_user) {
                this.is_special_user = is_special_user;
            }

            public void setLast_post_time(String last_post_time) {
                this.last_post_time = last_post_time;
            }

            public void setN_posts(int n_posts) {
                this.n_posts = n_posts;
            }

            public void setAlt(String alt) {
                this.alt = alt;
            }

            public void setLarge_avatar(String large_avatar) {
                this.large_avatar = large_avatar;
            }

            public void setId(String id) {
                this.id = id;
            }

            public void setIs_auth_author(boolean is_auth_author) {
                this.is_auth_author = is_auth_author;
            }

            public boolean isIs_followed() {
                return is_followed;
            }

            public String getEditor_notes() {
                return editor_notes;
            }

            public String getUid() {
                return uid;
            }

            public String getResume() {
                return resume;
            }

            public String getUrl() {
                return url;
            }

            public String getAvatar() {
                return avatar;
            }

            public String getName() {
                return name;
            }

            public boolean isIs_special_user() {
                return is_special_user;
            }

            public String getLast_post_time() {
                return last_post_time;
            }

            public int getN_posts() {
                return n_posts;
            }

            public String getAlt() {
                return alt;
            }

            public String getLarge_avatar() {
                return large_avatar;
            }

            public String getId() {
                return id;
            }

            public boolean isIs_auth_author() {
                return is_auth_author;
            }
        }

        public static class ThumbsEntity {
            /**
             * url : https://img1.doubanio.com/view/presto/medium/public/t115349.jpg
             * width : 544
             * height : 268
             */

            private MediumEntity medium;
            private String description;
            /**
             * url : https://img1.doubanio.com/view/presto/large/public/t115349.jpg
             * width : 544
             * height : 268
             */

            private LargeEntity large;
            private String tag_name;
            /**
             * url : https://img1.doubanio.com/view/presto/small/public/t115349.jpg
             * width : 320
             * height : 157
             */

            private SmallEntity small;
            private int id;

            public void setMedium(MediumEntity medium) {
                this.medium = medium;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public void setLarge(LargeEntity large) {
                this.large = large;
            }

            public void setTag_name(String tag_name) {
                this.tag_name = tag_name;
            }

            public void setSmall(SmallEntity small) {
                this.small = small;
            }

            public void setId(int id) {
                this.id = id;
            }

            public MediumEntity getMedium() {
                return medium;
            }

            public String getDescription() {
                return description;
            }

            public LargeEntity getLarge() {
                return large;
            }

            public String getTag_name() {
                return tag_name;
            }

            public SmallEntity getSmall() {
                return small;
            }

            public int getId() {
                return id;
            }

            public static class MediumEntity {
                private String url;
                private int width;
                private int height;

                public void setUrl(String url) {
                    this.url = url;
                }

                public void setWidth(int width) {
                    this.width = width;
                }

                public void setHeight(int height) {
                    this.height = height;
                }

                public String getUrl() {
                    return url;
                }

                public int getWidth() {
                    return width;
                }

                public int getHeight() {
                    return height;
                }
            }

            public static class LargeEntity {
                private String url;
                private int width;
                private int height;

                public void setUrl(String url) {
                    this.url = url;
                }

                public void setWidth(int width) {
                    this.width = width;
                }

                public void setHeight(int height) {
                    this.height = height;
                }

                public String getUrl() {
                    return url;
                }

                public int getWidth() {
                    return width;
                }

                public int getHeight() {
                    return height;
                }
            }

            public static class SmallEntity {
                private String url;
                private int width;
                private int height;

                public void setUrl(String url) {
                    this.url = url;
                }

                public void setWidth(int width) {
                    this.width = width;
                }

                public void setHeight(int height) {
                    this.height = height;
                }

                public String getUrl() {
                    return url;
                }

                public int getWidth() {
                    return width;
                }

                public int getHeight() {
                    return height;
                }
            }
        }
    }
}
