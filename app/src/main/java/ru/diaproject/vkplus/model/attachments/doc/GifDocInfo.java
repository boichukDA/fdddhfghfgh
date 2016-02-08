package ru.diaproject.vkplus.model.attachments.doc;


import java.io.Serializable;

public class GifDocInfo extends DocInfo {

    public GifDocInfo() {
        super(DocType.GIF_DOC_TYPE);
    }

    public static class Preview implements Serializable{
        private Photo photo;

        public Photo getPhoto() {
            return photo;
        }

        public void setPhoto(Photo photo) {
            this.photo = photo;
        }

        public static class Photo implements Serializable{
            public static class Size implements Serializable{
                public enum SizeType{
                    S("s"), M("m"), O("0");

                    private String value;
                    SizeType(String m) {
                        value = m;
                    }
                }
                private String src;
                private int width;
                private int height;
                private SizeType type;

                public String getSrc() {
                    return src;
                }

                public void setSrc(String src) {
                    this.src = src;
                }

                public int getWidth() {
                    return width;
                }

                public void setWidth(int width) {
                    this.width = width;
                }

                public int getHeight() {
                    return height;
                }

                public void setHeight(int height) {
                    this.height = height;
                }

                public SizeType getType() {
                    return type;
                }

                public void setType(SizeType type) {
                    this.type = type;
                }
            }

            private  Size sizeS;
            private  Size sizeM;

            public Size getSizeM() {
                return sizeM;
            }

            public void setSizeM(Size sizeM) {
                this.sizeM = sizeM;
            }

            public Size getSizeS() {
                return sizeS;
            }

            public void setSizeS(Size sizeS) {
                this.sizeS = sizeS;
            }
        }
    }

    private Preview preview;

    public Preview getPreview() {
        return preview;
    }

    public void setPreview(Preview preview) {
        this.preview = preview;
    }

}
