/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
#include "unqlite/unqlite.h"
#include "unqlite_cursor.h"

//取得键
JNIEXPORT jint JNICALL Java_com_opdar_unqlite_UnqliteCursor_key(JNIEnv * env,
		jobject obj, jbyteArray arr, jint length) {
	unqlite_int64 size = length;
	jbyte buffer[size];
	rc = unqlite_kv_cursor_key(pCursor, buffer, &size);
	(*env)->SetByteArrayRegion(env, arr, 0, size, buffer);
	return rc;
}

//取得值
JNIEXPORT jint JNICALL Java_com_opdar_unqlite_UnqliteCursor_value(JNIEnv * env,
		jobject obj, jbyteArray arr, jint length) {
	unqlite_int64 size = length;
	jbyte buffer[size];
	rc = unqlite_kv_cursor_data(pCursor, buffer, &size);
	(*env)->SetByteArrayRegion(env, arr, 0, size, buffer);
	return rc;
}

JNIEXPORT jint JNICALL Java_com_opdar_unqlite_UnqliteCursor_keyLength(
		JNIEnv * env, jobject obj) {
	unqlite_int64 length;
	rc = unqlite_kv_cursor_key(pCursor, NULL, &length);
	return length;
}

JNIEXPORT jint JNICALL Java_com_opdar_unqlite_UnqliteCursor_dataLength(
		JNIEnv * env, jobject obj) {
	unqlite_int64 length;
	rc = unqlite_kv_cursor_data(pCursor, NULL, &length);
	return length;
}

//关闭游标
JNIEXPORT jint JNICALL Java_com_opdar_unqlite_UnqliteCursor_close(JNIEnv * env,
		jobject obj) {
	unqlite_kv_cursor_release(pDb, pCursor);
	return rc;
}

JNIEXPORT jint JNICALL Java_com_opdar_unqlite_UnqliteCursor_moveToFrist(
		JNIEnv * env, jobject obj) {
	rc = unqlite_kv_cursor_first_entry(pCursor);
	return rc;
}

JNIEXPORT jint JNICALL Java_com_opdar_unqlite_UnqliteCursor_delete(JNIEnv * env,
		jobject obj) {
	rc = unqlite_kv_cursor_delete_entry(pCursor);
	return rc;
}

JNIEXPORT jint JNICALL Java_com_opdar_unqlite_UnqliteCursor_moveToLast(
		JNIEnv * env, jobject obj) {
	rc = unqlite_kv_cursor_last_entry(pCursor);
	return rc;
}

JNIEXPORT jint JNICALL Java_com_opdar_unqlite_UnqliteCursor_valid(JNIEnv * env,
		jobject obj) {
	rc = unqlite_kv_cursor_valid_entry(pCursor);
	return rc;
}

JNIEXPORT jint JNICALL Java_com_opdar_unqlite_UnqliteCursor_next(JNIEnv * env,
		jobject obj) {
	rc = unqlite_kv_cursor_next_entry(pCursor);
	return rc;
}

JNIEXPORT jint JNICALL Java_com_opdar_unqlite_UnqliteCursor_previous(
		JNIEnv * env, jobject obj) {
	rc = unqlite_kv_cursor_prev_entry(pCursor);
	return rc;
}

//追加内容
JNIEXPORT jint JNICALL Java_com_opdar_unqlite_UnqliteDatabase_append(
		JNIEnv * env, jobject obj, jstring jkey, jstring jvalue) {
	char *key = (char *) (*env)->GetStringUTFChars(env, jkey, 0);
	char *value = (char *) (*env)->GetStringUTFChars(env, jvalue, 0);
	int keylen = (*env)->GetStringUTFLength(env, jkey);
	int valuelen = (*env)->GetStringUTFLength(env, jvalue);
	rc = unqlite_kv_append(pDb, key, keylen, value, valuelen);
	(*env)->ReleaseStringUTFChars(env, jkey, key);
	(*env)->ReleaseStringUTFChars(env, jvalue, value);
	return rc;
}
