package org.gpginc.ntateam.apptest.runtime.util.annotation;

import org.gpginc.ntateam.apptest.runtime.util.enums.Rarity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.CLASS)
public @interface RarityHandler
{
    Rarity rarity();
}
