## 风险对象

| 项目 | 内容 |
| --- | --- |
| 库表 | `pupu_shopping_cart`.`shoppingcartitemextend` |
| 业务含义 | 购物车商品项拓展表 |
| 关联主表 | `shoppingcarts`（购物车主表） |
| 风险项 1 | MySQL 未分区表容量 <= 51200 MB → **不通过**（当前 ~122 GB） |
| 风险项 2 | MySQL 未分区表行数 <= 20000000 → **不通过**（当前 ~224M） |
| 集群 | `shoppingcart`（prod-shoppingcart） |
| 无分区 | 是 |
