package com.quochuy.utils;

import com.quochuy.exception.DataInputException;
import com.quochuy.models.Avatar;
import com.quochuy.models.User;
import com.cloudinary.utils.ObjectUtils;
import com.quochuy.models.product.Image;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UploadUtil {
    public static final String AVATAR_UPLOAD_FOLDER = "avatar_images";
    public static final String IMAGE_UPLOAD_FOLDER = "image_images";

    public Map buildAvatarUploadParams(Avatar avatar) {
        if (avatar == null || avatar.getId() == null)
            throw new DataInputException("Không thể upload hình ảnh chưa được lưu");

        String publicId = String.format("%s/%s", AVATAR_UPLOAD_FOLDER, avatar.getId());

        return ObjectUtils.asMap(
                "public_id", publicId,
                "overwrite", true,
                "resource_type", "image"
        );
    }

    public Map buildImageUploadParams(Image image) {
        if (image == null || image.getId() == null)
            throw new DataInputException("Không thể upload hình ảnh chưa được lưu");

        String publicId = String.format("%s/%s", IMAGE_UPLOAD_FOLDER, image.getId());

        return ObjectUtils.asMap(
                "public_id", publicId,
                "overwrite", true,
                "resource_type", "image"
        );
    }

    public Map buildImageDestroyParams(User user, String publicId) {
        if (user == null)
            throw new DataInputException("Không thể xoas hình ảnh không xác định");

        return ObjectUtils.asMap(
                "public_id", publicId,
                "overwrite", true,
                "resource_type", "image"
        );
    }

}
