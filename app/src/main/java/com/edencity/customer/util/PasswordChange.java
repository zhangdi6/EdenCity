package com.edencity.customer.util;

import android.support.annotation.NonNull;
import android.text.method.PasswordTransformationMethod;
import android.view.View;


public class PasswordChange extends PasswordTransformationMethod {
    @Override
    public CharSequence getTransformation(CharSequence source, View view) {
        return new PasswordCharSequence(source);
    }

    private class PasswordCharSequence implements CharSequence {
        private CharSequence charSequence;
        public PasswordCharSequence(CharSequence source) {
            charSequence = source ;
        }

        @Override
        public int length() {
            return charSequence.length();
        }

        @Override
        public char charAt(int i) {
            return '*';
        }

        @NonNull
        @Override
        public CharSequence subSequence(int i, int i1) {
            return charSequence.subSequence(i,i1);
        }
    }
}
