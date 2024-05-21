package j3lcardmarket.atelier2.commons.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.*;
import java.util.Base64;

@Component
public class SignatureUtils {

    @Autowired
    public PublicKey publicKey;
    @Autowired
    public PrivateKey privateKey;

    public String sign(String src){
        Signature signer;
        try {
            signer = Signature.getInstance("SHA256withRSA");
            signer.initSign(privateKey);
            signer.update(src.getBytes());
            byte[] signature = signer.sign();
            return src.replace(";", "\\;") + ";" + Base64.getEncoder().encodeToString(signature);
        } catch (NoSuchAlgorithmException | SignatureException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    public String unsign(String signed) throws SignatureException{
        String[] parts = signed.split("(?<!\\\\);", 2);
        if (parts.length != 2) throw new SignatureException("String not signed");
        String src = parts[0].replace("\\;", ";");
        byte[] signature = Base64.getDecoder().decode(parts[1]);
        try {
            Signature verifier = Signature.getInstance("SHA256withRSA");
            verifier.initVerify(publicKey);
            verifier.update(src.getBytes());
            boolean isValid = verifier.verify(signature);
            if (!isValid) throw new SignatureException("Invalid signature");
            return src;
        } catch (NoSuchAlgorithmException | SignatureException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }



}
