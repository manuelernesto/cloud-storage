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

    public void saveCredential(Credential credential) {

        if (credential.getCredentialid() != null)
            updateCredential(credential);
        else
            newCredential(credential);

    }


    public void deleteCredential(Integer credentialid) {
        credentialMapper.delete(credentialid);
    }


    private void newCredential(Credential credential) {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);

        credential.setKey(encodedKey);
        credential.setPassword(encryptedPassword);

        credentialMapper.insert(credential);
    }

    private void updateCredential(Credential credential) {
        Credential credentialDB = credentialMapper.getCredential(credential.getCredentialid());

        if (!credentialDB.getPassword().equals(credential.getPassword())) {
            String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), credential.getKey());
            credential.setPassword(encryptedPassword);
        }

        credentialMapper.update(credential);
    }
}
