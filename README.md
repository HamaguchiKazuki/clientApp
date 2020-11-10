# clientApp
client側  
server側を先に起動してね

追加プラグイン

build.gradle
``` bash
plugins {
    id 'kotlin-android-extensions'
}
```

# 責務
- ボタンを押されたらガチャを実行する役割
- Serviceに接続・切断する役割
- サーバ側で定義されたServiceを呼ぶ役割
