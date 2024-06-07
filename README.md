# Masked Secure Key Demonstration

This is a demo project made to demonstrate the usage of `Bytemask - Android Gradle Plugin` ([Read More](https://patilshreyas.github.io/bytemask/introduction.html)) by [Shreyas Patil](https://github.com/PatilShreyas)

We're used to include our secure credentials as a `BuildConfig` our gradle configuration files. 

```kts
android {
  
  productFlavours {
      create("release") {
            buildConfigField(Int::class.java.simpleName, API_KEY, "\"API KEY VALUE\"")
      }
  }
  
}
```

The project is set up to use the SHA-256 certificate from the `release` build variant, meaning that an reverse-engineerd app instance of this application won't be able to decrypt the credentials.

We're interested in the following code blocks in `app/build.gradle` configuration.

```kotlin
bytemaskConfig {
    // Strings to read from
    defaultPropertiesFileName.set("secure.properties")

    // Class name for the generated class
    className.set("MaskedConfig")

    configure("release") {
        enableEncryption = true
        encryptionKeySource = KeySource.SigningConfig("release")
    }
}
```

Next, we just need to store the encryped/masked credentials in `secure.properties` file

```env
// secure.properties
API_KEY=ZGVtbzpwQDU1dzByZA==
WHATEVER_SECRET=DJ393kDJ4K12K
```

### Demonstration
<img width="434" alt="Screenshot 2024-06-07 at 6 02 09 PM" src="https://github.com/NaingAungLuu/masked-secure-keys-demo/assets/45328771/ebdec5aa-63ca-4236-be5e-566d5a965b9a">
