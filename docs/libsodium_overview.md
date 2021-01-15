# Cross-platform cryptography

## Libsodium overview

This chapter is about a library called **Libsodium** that is also know with **NACL** or **SODIUM**. It's most advantage is that it is available for almost all major frameworks and provides cryptography methods that are "state of the art".

You will find authenticated encryption, private-public-key encryption and private-public-key signatures but none of them is "old fashioned". Below you get an overview of the functionality of this library:

The **authenticated encryption** has it's basis in the **XSalsa20 stream cipher**, combined with **Poly1305 MAC** authentication ("secret box"). 

The **private-public-key encryption** uses the **X25519 curve for key exchange**, the **XSalsa20 stream cipher** combined with **Poly1305 MAC** for authenticated encryption ("crypto box").

A special form of the private-public-key encryption is the **anonymus encryption** (called "sealed cryto box") where just the private-public key pair of the recipient will be used.

For generating a digital **signature** the **Ed25519 curve** will be used.

Do you need a **hash** or "fingerprint" of a string or file? Ok, Libsodium uses the **Blake2b hash algorithm** for that task.

If want to derive an encryption key from a passphrase it will be generated with **Argon2 algorithm**.

Giving a summary you may say "*I have never heard the names of the algorithms before*" and you are right... me too! That's the reason why I did not try to find a "cross platform solution" as there is no **AES**, **RSA** or **traditional EC curve** cryptography available in Libsodium. When viewing the sourcecodes you will notice that all **Libsodium programs are short** compared to the traditional ones because it is a high developed library that does a lot in the background.

A more detailed overview is available with the original [Libsodium documentation](https://doc.libsodium.org/) that is written in and for the "C" framework but the functionality is equal on all bindings for tother languages.

But the good news are: there are bindings available for Java, PHP (built-in since PHP 7.x), C#, NodeJs and for browser usage and I'm providing solutions for all major cryptography tasks like key encryption, private-public encryption and signing.

Here are my articles regarding Libsodium themes:

| Solution | Description | Java | PHP | C# | NodeJs | Browser
| ------ | :------: | :--: |:--: |:--: |:--: |:--: |
[Generate a X25519 key pair](x25519_key_generation.md) | generate a X25519 private key and public key pair in Base64 encoding | :soon: | :soon: |  :soon: |  :soon: |  :white_check_mark: |  
[Generate the X25519 public key from a secret key](x25519_public_key_generation.md) | generate the X25519 public key from a secret key in Base64 encoding |  :soon: |  :soon: |  :soon: |  :soon: |  :white_check_mark: |  
authenticated string encryption with a random key | uses "secret boxes" | :soon: |  :soon: |  :soon: |  :soon: |  :soon: |  
authenticated string encryption with a passphrase | uses "secret boxes" | :soon: |  :soon: |  :soon: |  :soon: |  :soon: |  
[authenticated hybrid string encryption with a private-public key exchange](libsodium_cryptobox_encryption_string.md) | uses "crypto boxes" | :white_check_mark: |  :white_check_mark: |  :white_check_mark: |  :white_check_mark: |  :white_check_mark: |  
anonymizised authenticated string hybrid encryption with a private-public key | uses "sealed boxes" | :soon: |  :soon: |  :soon: |  :soon: |  :soon: |  
sign a string with a private-public key |  | :soon: |  :soon: |  :soon: |  :soon: |  :soon: | 
hash a string |  | :soon: |  :soon: |  :soon: |  :soon: |  :soon: |  
generate a random key |  | :soon: |  :soon: |  :soon: |  :soon: |  :soon: |  
derive an encryption key from a passphrase |  | :soon: |  :soon: |  :soon: |  :soon: |  :soon: |  

### What additional libraries do I need to get the stuff working?

Below I'm providing the names and download links of all libraries I have used to run the examples. Please note that I did not take a deep care of the licenses of the libraries - please check them before using them in any (commercial) context.

| Framework | library | documentation | source and download link
| ------ | :------: | -- | -- |
| Java |  xsalsa20poly1305 version 0.11.0 |  [https://github.com/codahale/xsalsa20poly1305](https://github.com/codahale/xsalsa20poly1305)] | Github: [https://github.com/codahale/xsalsa20poly1305](https://github.com/codahale/xsalsa20poly1305)  Maven: [https://mvnrepository.com/artifact/com.codahale/xsalsa20poly1305](https://mvnrepository.com/artifact/com.codahale/xsalsa20poly1305) |
| PHP | built-in since PHP 7 >= 7.2.0 | [https://www.php.net/manual/en/book.sodium.php](https://www.php.net/manual/en/book.sodium.php) | GitHub:  [https://github.com/paragonie/pecl-libsodium-doc](https://github.com/paragonie/pecl-libsodium-doc)  |
| C# | libsodium.core version 1.2.3 | [https://github.com/tabrath/libsodium-core](https://github.com/tabrath/libsodium-core) | NuGet: [https://www.nuget.org/packages/Sodium.Core/](https://www.nuget.org/packages/Sodium.Core/) |
| NodeJs | TweetNaCl.js version 1.0.3 | Basic library  [https://github.com/dchest/tweetnacl-js](https://github.com/dchest/tweetnacl-js) | [https://github.com/dchest/tweetnacl-js](https://github.com/dchest/tweetnacl-js). |
| NodeJs | Tweetnacl-util-js version 0.15.1 | neccessary for data encoding [https://github.com/dchest/tweetnacl-util-js](https://github.com/dchest/tweetnacl-util-js) | [https://github.com/dchest/tweetnacl-util-js](https://github.com/dchest/tweetnacl-util-js)
| Browser | TweetNaCl.js version 1.0.3 | Basic library  [https://github.com/dchest/tweetnacl-js](https://github.com/dchest/tweetnacl-js) | [https://github.com/dchest/tweetnacl-js](https://github.com/dchest/tweetnacl-js). My programs are tested with  [nacl.min.js](https://github.com/dchest/tweetnacl-js/blob/master/nacl.min.js) |
| Browser | Tweetnacl-util-js version 0.15.1 | neccessary for data encoding [https://github.com/dchest/tweetnacl-util-js](https://github.com/dchest/tweetnacl-util-js) | [https://github.com/dchest/tweetnacl-util-js](https://github.com/dchest/tweetnacl-util-js) I used this file: [nacl-util.js](https://github.com/dchest/tweetnacl-util-js/blob/master/nacl-util.js)

Last update: Jan. 15th 2021

Back to the main page: [readme.md](../readme.md)
