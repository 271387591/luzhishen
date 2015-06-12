package com.ozstrategy.util;

import java.util.Map;

public class RSATester {

    static String publicKey;
    static String privateKey;

    static {
        try {
            Map<String, Object> keyMap = RSAUtils.genKeyPair();
            publicKey = RSAUtils.getPublicKey(keyMap);
            privateKey = RSAUtils.getPrivateKey(keyMap);
            System.err.println("公钥: \n\r" + publicKey);
            System.err.println("私钥： \n\r" + privateKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
   
    public static void main(String[] args) throws Exception {
//        test();
        testSign();
    }

    static void test() throws Exception {
        System.err.println("公钥加密——私钥解密");
        String source = "这是一行没有任何意义的文字，你看完了等于没看akfjlajlfjlaksjflkjaslfjalsdflf奥斯卡级放大了敬爱是法律进拉萨的风景连接阿斯顿发链接就是地方看了就撒旦了附近的垃圾收费立即爱上了对方家拉屎的减肥拉丝级东方了就爱上了飞机爱上了对方拉丝级东方了，不是吗？";
        System.out.println("\r加密前文字：\r\n" + source);
        byte[] data = source.getBytes();
        byte[] encodedData = RSAUtils.encryptByPublicKey(data, publicKey);
        System.out.println("加密后文字：\r\n" + new String(encodedData));
        byte[] decodedData = RSAUtils.decryptByPrivateKey(encodedData, privateKey);
        String target = new String(decodedData);
        System.out.println("解密后文字: \r\n" + target);
    }

    static void testSign() throws Exception {
        System.err.println("私钥加密——公钥解密");
        String source = "{\"id\":4,\"orderNo\":\"15052823480004\",\"notify_url\":\"http://kksdjfkj\",\"money\":2000.0}";
        System.out.println("原文字：\r\n" + source);
        publicKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC0B21R0OM04Kz1ZpmhUS+zrgZI4p8RTCnMO3hrIv0wMY+Bpho6ks7QKJ34NgxjPIq9FxwIrON5UJWADnYDM/Zu7XDmLqQGSWWIFsILZvySma7oH8JCpgx/EoEIE2Ic1pRyfFMyxfToqhLKF8iaFX6HLQRGnpmAvrq0zznhWIRP4wIDAQAB";
        privateKey="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALQHbVHQ4zTgrPVmmaFRL7OuBkjinxFMKcw7eGsi/TAxj4GmGjqSztAonfg2DGM8ir0XHAis43lQlYAOdgMz9m7tcOYupAZJZYgWwgtm/JKZrugfwkKmDH8SgQgTYhzWlHJ8UzLF9OiqEsoXyJoVfoctBEaemYC+urTPOeFYhE/jAgMBAAECgYBi8vYWEv7ekGLgGr10GgBi/NJ9evm88yTCGSnNkRSiSYs70kT2TryXUsvIokRawurqlWoWJph8lFsnYEti5UubrqY2pL6hUBi1gfyiB1IT9trEdOW3oyNVkzH/qBuZcIr4j8jixhFVwBakxD7QfsKFZTeFnMzRrVXXnWpor2H32QJBANooclVtfuz/wSZNU9KioSUT822yUT+fSGup9iTdshaTQMQLd7WkiaUM3nlmZ+X/SMOR+22LJ+3qRUOf/92DOfcCQQDTQdHUz9UrgGGcPpchIejrphyPl55FXX6yyGjQSEuXkfoAws4f++nNKqvpopZE0y7dHOedF9MBMAgokLvARz51AkBBnDR2mM25fVmbqVnyWVLZkdx+O0jQN5lSN/V9NOegfnzV+RTxniB/cRVfsXrB4zsBBmB1m4pQtUpLgk0zU4oVAkEAkBRu4J6Se2XGrMI/Xph6++TBy+kTR6m5VIO8gHkeHRSxFj3GSSgNUCFPODvsEqqZZrJlAM4T2daBafE0K7IrVQJBAMbRuFgLwgGJH+RQOr8VpQPK2UKuYPwzRF/AaeZDRGfheuj0+oe6NAhw/B3h/jYQRIZpwgFSkj0Mvx9mteZBt0Q=";
        byte[] data = source.getBytes();
        byte[] encodedData = RSAUtils.encryptByPrivateKey(data, privateKey);
        System.out.println("加密后：\r\n" + Base64Utils.encode(encodedData));
        byte[] decodedData = RSAUtils.decryptByPublicKey(encodedData, publicKey);
        String target = new String(decodedData);
        System.out.println("解密后: \r\n" + target);
        System.err.println("私钥签名——公钥验证签名");
        String sign = RSAUtils.sign(encodedData, privateKey);
        System.err.println("签名:\r" + sign);
        boolean status = RSAUtils.verify(encodedData, publicKey, sign);
        System.err.println("验证结果:\r" + status);
    }
   
}
