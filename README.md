# ComposeSample  
Composeメモ用リポジトリ


## アーキテクチャ
MVI  
<img src="https://github.com/hayashitoshiki/ComposeSample/blob/feature_mvi/picture/ComposeSample-MVI.drawio.png" width="300">　  
※Presenter層のみ記載
### 各項目
*  Screen：画面定義、Action定義、状態監視
*  Contant：画面描画
*  ViewModel：UIロジック
*  Contract：画面で用いるState、Effect、Event定義
    * State：画面の状態保持
    * Effect：画面の動作定義
    * Event：画面のイベント定義
* Data：Stateの各状態を取り出したもの


### 処理フロー  
1. Contentでイベント発火
2. Screenへ伝達し、ScreenからViewModelへ伝達
3. ViewModelで該当するUIロジック処理発火
##### --> データ更新の場合：
4. Stateの該当するデータを更新
5. Screenへ伝達
6. ContentへDataを渡し、Viewを更新
##### --> UIイベント実施の場合：
4. Effectへ指定のUIイベントを投入
5. Screenへ伝達
6. イベント処理



## ソース
### 全体
https://github.com/hayashitoshiki/ComposeSample/tree/master/app/src/main/java/com/myapp/composesample
### 詳細
* タブサンプル
https://github.com/hayashitoshiki/ComposeSample/blob/master/app/src/main/java/com/myapp/composesample/util/component/TabComponent.kt



