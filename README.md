* 目的
BLEのデータは，SwiftやAndroidでデータの送信部を実装した場合，短時間内に連続的に更新されることがある（例 5秒以内に２度データが送信されるなど)
そこで，そのような短時間のデータの更新をスルーするラッパークラスが必要であったため，実装する

* 実行環境＋依存ライブラリ
- 詳細については，pom.xmlを参照のこと
- Java: 1.7.0_75
- Jersey: 2.21
- jsoup: 1.72


