package com.quochuy.services.user;

import com.quochuy.exception.DataInputException;
import com.quochuy.models.Avatar;
import com.quochuy.models.dto.UserDTO;
import com.quochuy.models.dto.UserRegisterDTO;
import com.quochuy.models.enums.FileType;
import com.quochuy.models.InfoUser;
import com.quochuy.models.LocationRegion;
import com.quochuy.models.User;
import com.quochuy.models.UserPrinciple;
import com.quochuy.repositorys.*;
import com.quochuy.services.upload.UploadService;
import com.quochuy.utils.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UploadUtil uploadUtil;

    @Autowired
    private UploadService uploadService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private InfoUserRepository infoUserRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AvatarRepository avatarRepository;

    @Autowired
    private LocationRegionRepository locationRegionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User getById(Long id) {
        return userRepository.getById(id);
    }

    @Override
    public List<User> findAllByDeletedIsFalse() {
        return userRepository.findAllByDeletedIsFalse();
    }

    @Override
    public List<UserDTO> getAllUserDTOByDeletedIsFalse() {
        return userRepository.getAllUserDTOByDeletedIsFalse();
    }

    @Override
    public List<User> findAllByIdNot(long id) {
        return userRepository.findAllByIdNot(id);
    }

    @Override
    public Boolean existsByIdEquals(long id) {
        return userRepository.existsByIdEquals(id);
    }

    @Override
    public void softDelete(long userId) {
        userRepository.softDelete(userId);
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.getByUsername(username);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<UserRegisterDTO> findUserDTOByUsername(String username) {
        return userRepository.findUserDTOByUsername(username);
    }

    @Override
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User saveWithAvatar(User user, MultipartFile avatarFile) {
        LocationRegion locationRegion = user.getLocationRegion();
        locationRegion.setId(0L);
        LocationRegion newLocationRegion = locationRegionRepository.save(locationRegion);

        InfoUser infoUser = user.getInfoUser();
        infoUser.setId(0L);
        InfoUser newInfoUser = infoUserRepository.save(infoUser);

        user.setLocationRegion(newLocationRegion);
        user.setInfoUser(newInfoUser);

        Avatar avatar = new Avatar();

        String fileType = avatarFile.getContentType();

        assert fileType != null;

        fileType = fileType.substring(0, 5);

        avatar.setFileType(fileType);
        Avatar newAvatar = avatarRepository.save(avatar);

        user.setAvatar(newAvatar);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User newUer = userRepository.save(user);

        if (fileType.equals(FileType.IMAGE.getValue())) {
            uploadAndSaveUserAvatar(avatarFile, newUer, newAvatar);
        }

        return newUer;
    }

    private void uploadAndSaveUserAvatar(MultipartFile avatarFile, User user, Avatar avatar) {
        try {
            Map uploadResult = uploadService.uploadImage(avatarFile, uploadUtil.buildAvatarUploadParams(avatar));
            String fileUrl = (String) uploadResult.get("secure_url");
            String fileFormat = (String) uploadResult.get("format");

            avatar.setFileName(avatar.getId() + "." + fileFormat);
            avatar.setFileUrl(fileUrl);
            avatar.setFileFolder(UploadUtil.IMAGE_UPLOAD_FOLDER);
            avatar.setCloudId(avatar.getFileFolder() + "/" + avatar.getId());
            avatarRepository.save(avatar);

        } catch (IOException e) {
            e.printStackTrace();
            throw new DataInputException("Upload hình ảnh thất bại");
        }
    }

    @Override
    public void remove(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (!userOptional.isPresent()) {
            throw new UsernameNotFoundException(username);
        }
        return UserPrinciple.build(userOptional.get());
    }

}
