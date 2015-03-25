# webUIAuto
基于Java selenium的web 自动化

## 代码主体结构

### .properties

定义页面元素的xpath

### PageObject

- 封装页面的元素对象，并根据xpath初始化
- 封装页面的常用方法

### TestCase

业务脚本逻辑，测试用例

### testdata

存放的测试数据

## Reporter
参照网易的[arrow](https://github.com/NetEase/arrow)，可以在结果html中插入截图

## DateProvider
没有使用TestNG的dataprovider，主要是一开始有考虑把测试过程中，产生的数据，导出来，testng的dataprovider还没有深入了解是否可以导出。

## Demo
UI_Demo中，baidu的首页
