package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * Password Management: Save, edit, and delete website credentials.
 * When the user views the credential, they should be able to see the unencrypted password.
 */
@Service
public class CredentialService {

    private final EncryptionService encryptionService;
    private final CredentialMapper credentialMapper;

    public CredentialService(EncryptionService encryptionService, CredentialMapper credentialMapper) {
        this.encryptionService = encryptionService;
        this.credentialMapper = credentialMapper;
    }

    // Helper Method -- to generate an encoded key
    public String generateEncodedKey () {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        return encodedKey;
    }

    // add Credential
    public Integer addCredential (CredentialForm credentialForm) {
        String encodedKey = this.generateEncodedKey();
        String encryptedPassword = encryptionService.encryptValue(credentialForm.getPassword(), encodedKey);
        Credential newCredential = new Credential(credentialForm.getUrl(), credentialForm.getUsername(), encryptedPassword, credentialForm.getUserid(), encodedKey);
        credentialMapper.addCredential(newCredential);
        return newCredential.getCredentialid();
    }

    // edit Credential
    public void updateCredential (CredentialForm credentialForm) {
        String encodedKey = this.generateEncodedKey();
        Credential updateCredential = credentialMapper.getCredentialById(credentialForm.getCredentialid());
        updateCredential.setUrl(credentialForm.getUrl());
        updateCredential.setUsername(credentialForm.getUsername());
        updateCredential.setKey(encodedKey);
        String encryptedPassword = encryptionService.encryptValue(credentialForm.getPassword(), encodedKey);
        updateCredential.setPassword(encryptedPassword);
        credentialMapper.updateCredential(updateCredential);
    }

    // remove credential -- return true if success
    public boolean deleteByCredentialid (Integer credentialid) {
        if (credentialMapper.getCredentialById(credentialid) != null) {
            credentialMapper.deleteCredentialByCredentialid(credentialid);
            return true;
        }
        return false;
    }

    // get credential by its id with Encrypted password
    public Credential getCredential (Integer credentialid) {
        Credential viewCredential = credentialMapper.getCredentialById(credentialid);
        if (viewCredential == null) {
            return null;
        }
        return viewCredential;
    }

    // get credential by its id with Decrypted password
    public Credential getCredentialDecrypted (Integer credentialid) {
        Credential viewCredential = credentialMapper.getCredentialById(credentialid);
        if (viewCredential == null) {
            return null;
        }
        // method guide -- String decryptedPassword = encryptionService.decryptValue(encryptedPassword, encodedKey);
        String encodedKey = viewCredential.getKey();
        String encryptedPassword = viewCredential.getPassword();
        String decryptedPassword = encryptionService.decryptValue(encryptedPassword, encodedKey);
        viewCredential.setPassword(decryptedPassword);
        return viewCredential;
    }

    // get credentials by user id with ENCRYPTED password
    public List<Credential> getCredentialsByUser (Integer userid) {
        List<Credential> credentials = credentialMapper.getCredentialsByUserid(userid);
        return credentials;
    }

    // get credentials by user id with DECRYPTED password -- will be useful in 'showCredentialModal'
    public List<Credential> getCredentialsDEByUser (Integer userid) {
        List<Credential> credentials = credentialMapper.getCredentialsByUserid(userid);
        List<Credential> res = new ArrayList<>();
        for (Credential credential : credentials) {
            String encodedKey = credential.getKey();
            String encryptedPassword = credential.getPassword();
            String decryptedPassword = encryptionService.decryptValue(encryptedPassword, encodedKey);
            credential.setPassword(decryptedPassword);
            res.add(credential);
        }
        return res;
    }

    // get userid from credential -- a method for adding @preAuthorize at @CredentialController
    public Integer getUseridByCredentialid (Integer credentialid) {
        Credential theCredential = credentialMapper.getCredentialById(credentialid);
        if (theCredential != null) {
            return theCredential.getUserid();
        }
        return -1;
    }
}
