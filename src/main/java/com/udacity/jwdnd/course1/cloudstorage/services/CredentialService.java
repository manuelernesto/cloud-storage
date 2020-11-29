package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    private final EncryptionService encryptionService;
    private final CredentialMapper credentialMapper;

    public CredentialService(EncryptionService encryptionService, CredentialMapper credentialMapper) {
        this.encryptionService = encryptionService;
        this.credentialMapper = credentialMapper;
    }

    public List<Credential> getCredentials(Integer userid) {
        List<Credential> credentials = new ArrayList<>();

        credentialMapper.getCredentials(userid).forEach(
                credential -> {
                    credential.setPassword(
                            encryptionService.decryptValue(credential.getPassword(), credential.getKey()));
                    credentials.add(credential);

                }
        );


        return credentials;
    }

    public boolean saveCredential(Credential credential) {

        if (credential.getCredentialid() != null)
            return updateCredential(credential);
        else
            return newCredential(credential);

    }


    public boolean deleteCredential(Integer credentialid) {
        return credentialMapper.delete(credentialid);
    }


    private boolean newCredential(Credential credential) {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);

        credential.setKey(encodedKey);
        credential.setPassword(encryptedPassword);

        return credentialMapper.insert(credential);
    }

    private boolean updateCredential(Credential credential) {
        Credential credentialDB = credentialMapper.getCredential(credential.getCredentialid());

        if (!credentialDB.getPassword().equals(credential.getPassword())) {
            String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), credentialDB.getKey());
            credential.setKey(credentialDB.getKey());
            credential.setPassword(encryptedPassword);
        }

        return credentialMapper.update(credential);
    }
}
