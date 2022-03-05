package com.myapp.composesample.util.Extension

// ========================================
// 　　　　　SealedClass拡張
//　　Enumメソッド拡張
//　　　・values
//　　　・valueOf
// ========================================

interface SealedClassEnumExtension<T>

interface SealedClassEnumWithName {
    val name get(): String = this::class.simpleName ?: "N/A"
}

inline fun <reified T> SealedClassEnumExtension<T>.values(): List<T> {
    return T::class.sealedSubclasses.mapNotNull { it.objectInstance }
}

inline fun <reified T : SealedClassEnumWithName> SealedClassEnumExtension<T>.valueOf(name: String): T {
    return values().find { it.name == name } ?: throw IllegalArgumentException(name)
}