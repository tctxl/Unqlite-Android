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
 */
package com.opdar.test;

import java.io.File;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.opdar.unqlite.UnqliteCursor;
import com.opdar.unqlite.UnqliteDatabase;
import com.opdar.unqlite.exception.UnqliteDatabaseException;


public class TestUnqlite extends Activity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        TextView  tv = new TextView(this); 
        String file = getFilesDir()+File.separator+"unqlite.db";
        UnqliteDatabase unqliteDatabase = new UnqliteDatabase();
        unqliteDatabase.onCreateDatabase(file, UnqliteDatabase.OPEN_CREATE);
        unqliteDatabase.open(file, UnqliteDatabase.OPEN_READWRITE); 
        boolean createOk = unqliteDatabase.replaceValue("base", "~細細~");
        unqliteDatabase.replaceValue("1base1", "~細細~");
        unqliteDatabase.replaceValue("1base2", "~細細~");
        unqliteDatabase.replaceValue("1base3", "~細細~");
        unqliteDatabase.replaceValue("1base4", "~細細~");
        unqliteDatabase.close();
        try {
            unqliteDatabase.open(file, UnqliteDatabase.OPEN_READONLY); 
			UnqliteCursor cursor = unqliteDatabase.query();
			while(cursor.isVaild()){
				System.out.println("Key : "+cursor.getKey());
				System.out.println("Value : "+cursor.getValue());
				cursor.next(); 
        	}
			cursor.close();
			unqliteDatabase.close();
			unqliteDatabase.open(file, UnqliteDatabase.OPEN_READWRITE); 
			UnqliteCursor cursor2 = unqliteDatabase.query("1base1");
			System.out.println("isValid1 : "+cursor2.isVaild());
			cursor2.delete();
			System.out.println("isValid2 : "+cursor2.isVaild());
			cursor2.close();
			unqliteDatabase.close();
		} catch (UnqliteDatabaseException e) {
			e.printStackTrace();
		}
        unqliteDatabase.close();        
        tv.setText( unqliteDatabase.stringFromJNI());
        setContentView(tv);
    }

}
