package com.myapp.composesample.ui.left

import android.util.Log
import com.myapp.composesample.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import javax.inject.Inject

/**
 * ロジック関連画面 UIロジック
 *
 */
@HiltViewModel
class FirstViewModel @Inject constructor() :
    BaseViewModel<FirstContract.State, FirstContract.Effect, FirstContract.Event>() {

    init{
        // テストデータ
        val sampleText = "テスト　変換できるかチェック"
        Log.d("暗号化", "実施前　＝　" + sampleText)
        val publicKeyString = "vT5DaDtrwEfwQIXNv6eAuAZ0WjQH+hXv"

        /** 暗号化 **/
        // 共通鍵
        val commonKey = createPublicKey()
        // 公開鍵
        val publicKey: SecretKey = SecretKeySpec(publicKeyString.toByteArray(), "AES")
        // 暗号化(土台作成) + 実施
        val cipher = Cipher.getInstance("AES/GCM/NOPADDING")
        cipher.init(Cipher.ENCRYPT_MODE, commonKey)
        val sampleText2 = cipher.doFinal(sampleText.toByteArray())
        /** 共通鍵とIVを公開鍵で暗号化 **/
        // 共通鍵とIVを公開鍵で暗号化
        val commonKeyEncode = commonKey.encoded
        val iv = cipher.iv
        // 共通鍵暗号化(土台作成) + 暗号化
        val keyCipher = Cipher.getInstance("AES/GCM/NOPADDING")
        keyCipher.init(Cipher.ENCRYPT_MODE, publicKey)
        val viCipher = Cipher.getInstance("AES/GCM/NOPADDING")
        viCipher.init(Cipher.ENCRYPT_MODE, publicKey)
        val encryptionCommonKey = keyCipher.doFinal(commonKeyEncode)
        val encryptionIv = viCipher.doFinal(iv)
        // 共通鍵とIVをString変換(Base64エンコーディング)
        val stringKey = Base64.getEncoder().encodeToString(encryptionCommonKey)
        val stringIv = Base64.getEncoder().encodeToString(encryptionIv)
        val keyCipherIv = keyCipher.iv
        val viCipherIv = viCipher.iv

        Log.d("暗号化", "暗号化　＝　" + sampleText2)

        /** 共通鍵とIVを公開鍵で復号化 **/
        // 共通鍵とIVをString変換(Base64変換)
        val decodeKey = Base64.getDecoder().decode(stringKey)
        val decodeIv = Base64.getDecoder().decode(stringIv)
        // 共通鍵暗号化(土台作成)
        val publicKeyIv = IvParameterSpec(keyCipherIv)
        val publicKeyIvIv = IvParameterSpec(viCipherIv)
        val keyRestoreCipher = Cipher.getInstance("AES/GCM/NOPADDING")
        keyRestoreCipher.init(Cipher.DECRYPT_MODE, publicKey, publicKeyIv)
        val ivRestoreCipher = Cipher.getInstance("AES/GCM/NOPADDING")
        ivRestoreCipher.init(Cipher.DECRYPT_MODE, publicKey, publicKeyIvIv)
        // 暗号化
        val decodeEncryptionCommonKey = keyRestoreCipher.doFinal(decodeKey)
        val decodeEncryptionIv = ivRestoreCipher.doFinal(decodeIv)
        // 共通鍵とIVを再生成
        val restoreKey = SecretKeySpec(decodeEncryptionCommonKey, "AES")
        val restoreIv = IvParameterSpec(decodeEncryptionIv)
        /** 復号化 **/
        // 複合化土台作成
        val restoreCipher = Cipher.getInstance("AES/GCM/NOPADDING")
        restoreCipher.init(Cipher.DECRYPT_MODE, restoreKey, restoreIv)
        // 復号化
        val byteResult = restoreCipher.doFinal(sampleText2)
        val sampleText3 = String(byteResult)
        Log.d("暗号化", "復号化　＝　" + sampleText3)
    }

    // 共通鍵生成
    private fun createPublicKey() : SecretKey {
        val keygen = KeyGenerator.getInstance("AES")
        keygen.init(256)
        return keygen.generateKey()
    }

    /**
     * Stateの初期化
     *
     * @return 初期化されたState
     */
    override fun initState(): FirstContract.State {
        return FirstContract.State()
    }

    /**
     * アクションとUIロジクの紐付け
     *
     * @param event アクション
     */
    override fun handleEvents(event: FirstContract.Event) = when (event) {
        is FirstContract.Event.NavigateToButtonGroupScreen -> setEffect { FirstContract.Effect.NavigateToButtonGroupScreen }
        is FirstContract.Event.NavigateToTextGroupScreen -> setEffect { FirstContract.Effect.NavigateToTextGroupScreen }
        is FirstContract.Event.NavigateToTextGroupExtraScreen -> setEffect { FirstContract.Effect.NavigateToTextGroupExtraScreen }
        is FirstContract.Event.NavigateToLogicScreen -> setEffect { FirstContract.Effect.NavigateToLogicScreen }
        is FirstContract.Event.NavigateToListGroupScreen -> setEffect { FirstContract.Effect.NavigateToListGroupScreen }
    }


}
