package com.example.clam314.changeavatar;

import java.util.List;

/**
 * Created by clam314 on 2016/4/28.
 */
public class ImageBean {
    /**
     *文件夹下其他图片路径
     */
    private List<String> imagesPath;

    /**
     * 文件夹的第一张图片路径
     */
    private String topImagePath;
    /**
     * 文件夹名
     */
    private String folderName;
    /**
     * 文件夹中的图片数
     */
    private int imageCounts;

    public List<String> getImagesPath() {
        return imagesPath;
    }

    public void setImagesPath(List<String> imagesPath) {
        this.imagesPath = imagesPath;
    }

    public String getTopImagePath() {
        return topImagePath;
    }
    public void setTopImagePath(String topImagePath) {
        this.topImagePath = topImagePath;
    }
    public String getFolderName() {
        return folderName;
    }
    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }
    public int getImageCounts() {
        return imageCounts;
    }
    public void setImageCounts(int imageCounts) {
        this.imageCounts = imageCounts;
    }

}
