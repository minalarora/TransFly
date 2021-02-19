# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-dontskipnonpubliclibraryclasses
-dontobfuscate
-forceprocessing
-optimizationpasses 5
 # for retrofit2
     -dontwarn retrofit2.**
     -keepattributes Signature
     -keepattributes Exceptions

     -keepclasseswithmembers class * {
     @retrofit2.http.* <methods>;
     }

     ## Square Picasso specific rules ##
     ## https://square.github.io/picasso/ ##

     -dontwarn com.squareup.okhttp.**
     -dontwarn com.squareup.picasso.**

     # OkHttp
     -keepattributes Signature
     -keepattributes *Annotation*

     -dontwarn okhttp3.**

     ## Google Play Services 4.3.23 specific rules ##
     ## https://developer.android.com/google/play-
     #Proguard ##



     -keep public class
     com.google.android.gms.common.internal.safeparcel.SafeParcelable {
       public static final *** NULL;
     }

     -keepnames @com.google.android.gms.common.annotation.KeepName class *
     -keepclassmembernames class * {
     @com.google.android.gms.common.annotation.KeepName *;
     }

     -keepnames class * implements android.os.Parcelable {
         public static final ** CREATOR;
     }

     # Okio
     -dontwarn java.nio.file.*
     -dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
     -dontwarn okio.**



     ##---------------Begin: proguard configuration common for all Android          apps ---------
     -optimizationpasses 5
     -dontusemixedcaseclassnames
     -dontskipnonpubliclibraryclasses
     -dontskipnonpubliclibraryclassmembers
     -dontpreverify
     -verbose
     -dump class_files.txt
     -printseeds seeds.txt
     -printusage unused.txt
     -printmapping mapping.txt
     -optimizations          !code/simplification/arithmetic,!field/*,!class/merging/*

     -allowaccessmodification
     -keepattributes *Annotation*
     -renamesourcefileattribute SourceFile
     -keepattributes SourceFile,LineNumberTable
     -repackageclasses ''

     -keep public class * extends android.app.Activity
     -keep public class * extends android.app.Application
     -keep public class * extends android.app.Service
     -keep public class * extends android.content.BroadcastReceiver
     -keep public class * extends android.content.ContentProvider
     -keep public class * extends android.app.backup.BackupAgentHelper
     -keep public class * extends android.preference.Preference
     -dontnote com.android.vending.licensing.ILicensingService

     # Explicitly preserve all serialization members. The Serializable interface
     # is only a marker interface, so it wouldn't save them.
     -keepclassmembers class * implements java.io.Serializable {
     static final long serialVersionUID;
     private static final java.io.ObjectStreamField[]
     serialPersistentFields;
     private void writeObject(java.io.ObjectOutputStream);
     private void readObject(java.io.ObjectInputStream);
     java.lang.Object writeReplace();
     java.lang.Object readResolve();
     }

     # Preserve all native method names and the names of their classes.
     -keepclasseswithmembernames class * {
      native <methods>;
      }

     -keepclasseswithmembernames class * {
         public <init>(android.content.Context, android.util.AttributeSet);
     }

     -keepclasseswithmembernames class * {
         public <init>(android.content.Context, android.util.AttributeSet,
      int);
     }

     # Preserve static fields of inner classes of R classes that might be
     # through introspection.
     -keepclassmembers class **.R$* {
       public static <fields>;
     }

     # Preserve the special static methods that are required in all
     -keepclassmembers enum * {
         public static **[] values();
         public static ** valueOf(java.lang.String);
     }

     -keep public class * {
         public protected *;
     }

     -keep class * implements android.os.Parcelable {
       public static final android.os.Parcelable$Creator *;
     }

## -------------Begin: Retrofit2 ---
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions
-keepattributes *Annotation*

-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}
-keepclassmembernames interface * {
        @retrofit.http.* <methods>;
}

## -------------End: Retrofit2 ---

##--- Begin:GSON ----
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-keep class sun.misc.Unsafe { *; }
#-keep class com.google.gson.stream.** { *; }

# Prevent proguard from stripping interface information from TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# keep enum so gson can deserialize it
-keepclassmembers enum * { *; }

##--- End:GSON ----

##--- Begin:GSON ----
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-keep class sun.misc.Unsafe { *; }
#-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.examples.android.model.** { *; }

# Prevent proguard from stripping interface information from TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# keep enum so gson can deserialize it
-keepclassmembers enum * { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class net.mreunionlabs.wob.model.request.** { *; }
-keep class net.mreunionlabs.wob.model.response.** { *; }
-keep class net.mreunionlabs.wob.model.gson.** { *; }
##--- End:GSON ----

-keep class * extends android.app.Activity
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
}