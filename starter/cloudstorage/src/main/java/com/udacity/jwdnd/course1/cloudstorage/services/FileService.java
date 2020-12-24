package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * user should be able to upload files and see any files they previously uploaded.
 * user should be able to view/download or delete previously-uploaded files.
 * Any errors related to file actions should be displayed. For example, a user should not be able to upload two files with the same name, but they'll never know unless you tell them!
 */
@Service
public class FileService {

    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    // get files
    public List<File> getFilesByUserid (Integer userid) {
        return fileMapper.getFilesByUserid(userid);
    }

    // get single file by its id
    public File getFileByFileid (Integer fileid) {
        return fileMapper.getFileByFileid(fileid);
    }

    // get single file by its name and userid
    public File getFileByFilenameAndUserid (String filename, Integer userid) {
        return fileMapper.getFileByFilenameAndUserid(filename, userid);
    }

    // add file -- its filename, contenttype, filedata.
    public Integer addFile (MultipartFile multipartFile, Integer userid) throws IOException {
        File newFile = new File();
        // check if new file name available
        if (! isFilenameAvailable(multipartFile.getOriginalFilename(), userid)) {
            return -1;
        } else {
            newFile.setFilename(multipartFile.getOriginalFilename());
        }
        newFile.setContenttype(multipartFile.getContentType());
        newFile.setFiledata(multipartFile.getBytes());
        newFile.setUserid(userid);
        fileMapper.addFile(newFile);
        return newFile.getFileid();
    }

    // remove file by its id -- return true if success.
    public boolean deleteByFileid (Integer fileid) {
        if (fileMapper.getFileByFileid(fileid) != null) {
            fileMapper.deleteByFileid(fileid);
            return true;
        }
        return false;
    }

    // get userid from fileid -- a method for adding @preAuthorize at @FileController
    public Integer getUseridByFileid (Integer fileid) {
        File theFile = fileMapper.getFileByFileid(fileid);
        Integer userid = theFile.getUserid();
        return userid;
    }

    // check if filename available
    public boolean isFilenameAvailable (String filename, Integer userid) {
        return fileMapper.getFileByFilenameAndUserid(filename, userid) == null;
    }
}
