首先建立class calabush作为葫芦娃类，其中包括用于表示葫芦娃序号的id，分别用于报告排名和自身颜色的rank和color同时提供了对外获取id、rank以及color的接口。\
在葫芦娃类之上建立葫芦兄弟类class calabushBrother，在葫芦兄弟类中实例化大娃到七娃七个葫芦娃对象，同时建立了一个int数组用于存储葫芦兄弟的排列。在葫芦兄弟类中可进行葫芦兄弟随机排列的生成、基于不同属性的报数、基于排名的冒泡排序以及基于颜色的二分插入排序。由于实际上葫芦兄弟的排名和颜色排序之间的大小关系并无差距，因此在实际实现中通过id排序即可。\
最后在main中实例化一个葫芦兄弟对象并基于题目要求依次完成生成随机排列、冒泡排序、排名报数、生成随机排列、二分插入排序、颜色报数。
