/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class sg_rt_oits_serial_SerialPort */

#ifndef _Included_sg_rt_oits_serial_SerialPort
#define _Included_sg_rt_oits_serial_SerialPort
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     sg_rt_oits_serial_SerialPort
 * Method:    open
 * Signature: (Ljava/lang/String;II)Ljava/io/FileDescriptor;
 */
JNIEXPORT jobject JNICALL Java_sg_rt_oits_serial_SerialPort_open
  (JNIEnv *, jobject, jstring, jint, jint);

/*
 * Class:     sg_rt_oits_serial_SerialPort
 * Method:    close
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_sg_rt_oits_serial_SerialPort_close
  (JNIEnv *, jobject);

#ifdef __cplusplus
}
#endif
#endif
