#include <jni.h>
#include <notify.h>

JNIEXPORT void JNICALL Java_sample_Platform_MessageForNotifyAboutCompleteDownloadProcess_showNativeNotifyMessage(
    JNIEnv *env,
    jobject javaObject,
    jstring java_mainTitle,
    jstring java_averageText,
    jstring java_smallText,
    jstring java_mainImagePath)
{
    const char *mainTitle = (*env)->GetStringUTFChars(env, java_mainTitle, 0),
               *averageText = (*env)->GetStringUTFChars(env, java_averageText, 0),
               *smallText = (*env)->GetStringUTFChars(env, java_smallText, 0),
               *mainImagePath = (*env)->GetStringUTFChars(env, java_mainImagePath, 0);

    notify_init(mainTitle);

    NotifyNotification *notifyNotification;

    notifyNotification = notify_notification_new(
        averageText, smallText, mainImagePath);

    notify_notification_set_timeout(notifyNotification, 10000);
    notify_notification_show(notifyNotification, 0);
}