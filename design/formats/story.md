# story 格式设计

## 1. 格式概述

story 格式是 GG Game Engine 为 Galgame 引擎专门设计的一种标记语言格式，基于 **notedown** 语法，用于编写游戏中的对话、剧情和交互内容。story 格式结合了 notedown 的简洁性和游戏脚本的专业性，提供了一种现代化的游戏剧本编写方式。

## 2. 设计目标

- **简洁易读**：基于 notedown 语法，保持简洁易读特点
- **易于解析**：由 oak-notedown 框架提供解析支持
- **功能强大**：支持分支、条件、变量等游戏脚本所需的功能
- **可扩展性**：支持自定义命令和扩展，满足不同游戏的需求

## 3. 基本语法

### 3.1 注释

使用 `#` 开头的行作为注释：

```md
# 这是注释内容
# 可以在任何地方使用
```

### 3.2 变量定义

使用 `{var=value}` 定义变量：

```md
{player_name = "主角"}
{sakura_affection = 0}
{knows_secret = false}
```

### 3.3 列表定义

使用 `{var=value}` 定义列表：

```md
{characters = [player, sakura, ren, yuki]}
{inventory = []}
```

### 3.4 文本显示

直接书写文本，或使用角色名：对话格式：

```md
星光学院，这所历史悠久的名校，隐藏着不为人知的秘密。

樱井美咲：你好！欢迎来到星光学院！

主角：你好！
```

### 3.5 场景定义

使用 `== 场景名 ==` 定义场景：

```md
== prologue ==
这是游戏的序章。

== chapter1_start ==
第一章开始。
```

### 3.6 选项分支

使用 `* [选项文本]` 定义选择项：

```md
* [好啊，我去看看]
    主角：好啊，我去看看！
    -> accept_student_council

* [抱歉，我还有事]
    主角：抱歉，我还有事。
    -> decline_student_council
```

### 3.7 条件选项

使用 `* {条件表达式} [选项文本]` 定义条件选项：

```md
* {sakura_affection >= 5} [特殊选项：深入了解]
    樱井美咲：其实...我有话想对你说。
    -> special_route
```

### 3.8 跳转

使用 `-> 目标` 进行跳转：

```md
-> next_scene
-> chapter1_school.start
-> DONE
```

```xml
<goto target="chapter1_school.start"/>
```

### 3.9 条件判断

#### 方法 1：使用 `{ - 条件: 内容 }` 格式

```md
{
    - knows_secret == true:
        主角：（我已经知道了秘密...）
    - else:
        主角：（希望能找到线索...）
}
```

#### 方法 2：使用 XML 扩展格式

```xml
<if knows_secret == true>
    主角：（我已经知道了秘密...）
<else>
    主角：（希望能找到线索...）
</if>
```

### 3.10 变量操作

使用 `{表达式}` 进行变量操作，等价于 `<script>表达式</script>`：

```md
{sakura_affection++}  <!-- 等价于 <script>sakura_affection++</script> -->
{ren_affection += 2}  <!-- 等价于 <script>ren_affection += 2</script> -->
{knows_secret = true} <!-- 等价于 <script>knows_secret = true</script> -->
```

#### 多行代码建议

对于多行代码，建议使用 XML 扩展格式：

```md
<script>
sakura_affection++;
ren_affection += 2;
knows_secret = true;
</script>
```

### 3.11 命令调用

使用 `\module::function()` 调用命令：

```md
\audio::play("bgm/theme.mp3", 0.6)
\scene::change("bg/school.png", "fade", 1.0)
\character::show("sakura", "smile", "left")
\effect::flash(0.3)
```

### 3.12 包含其他文件

使用 `\include` 包含其他文件（超级指令）：

```md
\include chapter1_school.story
\include chapter2_investigation.story
```

**超级指令说明**：`\include` 是超级指令，从解析器层面进行特殊处理，用于在编译时包含其他文件的内容。

### 3.13 导入语句

使用 `\using` 语句导入模块或函数（超级指令）：

```md
# 导入整个模块
\using game

# 导入特定函数
\using game::save_game

# 导入并指定别名
\using game::save_game as save
\using game::load_game as load

# 使用导入的函数
\save("save1.sav")
\load("save1.sav")
```

**超级指令说明**：`\using` 是超级指令，从解析器层面进行特殊处理，用于在编译时导入模块或函数，简化后续的命令调用。

### 3.14 分隔线

使用 `---` 作为分隔线：

```md
---

文件内容：
"记忆操纵实验 - 第七次测试报告"

---
```

### 3.15 原生 XML 支持

直接原生支持 XML 标签，用于实现复杂的字体效果：

#### 3.15.1 基本用法

直接在文本中使用 XML 标签：

```md
这是<em>斜体</em>文本，这是<strong>粗体</strong>文本。

樱井美咲：这是<em>重要</em>的消息
主角：我明白了，<strong>谢谢</strong>
```

#### 3.15.2 支持的标签

- `<em>`：斜体
- `<i>`：斜体
- `<strong>`：粗体
- `<b>`：粗体
- `<ruby>`：注音（Ruby 标注）
- `<font>`：字体设置
- `<span>`：行内容器

#### 3.15.3 示例

```md
# Ruby 标注
漢字<ruby>かんじ</ruby>

# 混合效果
这是<em>斜体</em>和<strong>粗体</strong>的混合效果

# 字体设置
<font color='red' size='14'>红色大字体</font>

# 复杂嵌套
这是<ruby>漢字<rt>かんじ</rt></ruby>，包含<em>斜体</em>和<strong>粗体</strong>的复杂效果，以及<font color='blue'>蓝色文本</font>。

# 角色对话中的 XML
樱井美咲：你好，<em>新同学</em>！欢迎来到<ruby>星光学院<rt>せいこうがくいん</rt></ruby>！
主角：谢谢，<strong>樱井同学</strong>！

# 道具描述中的 XML
---
小纸条内容：
"新来的<em>转学生</em>：
如果你对这所学校的<ruby>秘密<rt>ひみつ</rt></ruby>感兴趣，
今晚12点到<strong>旧校舍</strong>后面。
——一个知道真相的人"
---
```

#### 3.15.4 优势

1. **直接原生**：无需特殊语法，直接在文本中使用 XML 标签
2. **语法简洁**：直接使用 HTML 标签，避免了命令嵌套的复杂性
3. **灵活性高**：支持任意 HTML 标签和属性，实现更丰富的效果
4. **可读性强**：结构化的 XML 语法比嵌套命令更易读
5. **功能强大**：原生支持 Ruby 标注等复杂效果
6. **维护性好**：结构清晰，便于修改和维护
7. **扩展性强**：可以根据需要添加新的标签和属性

原生 XML 支持特别适合实现复杂的字体效果，尤其是在需要多种效果组合的场景中，相比传统命令写法具有明显的优势。

## 4. 高级功能

### 4.1 复杂条件表达式

支持复杂的条件表达式：

#### 方法 1：使用 `{ - 条件: 内容 }` 格式

```md
{
    - sakura_affection >= 8 && trust_points >= 10:
        -> sakura_true_ending
    - ren_affection >= 5 && knows_ren_secret == true:
        -> ren_good_ending
    - else:
        -> normal_ending
}
```

#### 方法 2：使用 XML 扩展格式

```md
<if sakura_affection &gt;= 8 && trust_points &gt;= 10>
    -> sakura_true_ending
<else-if ren_affection &gt;= 5 && knows_ren_secret == true>
    -> ren_good_ending
<else>
    -> normal_ending
</if>
```

### 4.2 多条件选项

支持多个条件选项：

```md
* {clues_found >= 3 && trust_points >= 5} [揭示真相]
    主角：我已经知道一切了。
    -> reveal_truth
```

### 4.3 循环功能

story 格式支持通过序列和跳转机制实现循环功能：

#### 4.3.1 基本循环

#### 方法 1：使用跳转和条件判断

```md
== count_loop ==
{count = 0}

-> loop_start

== loop_start ==
主角：这是第 {count} 次循环
{count++}

{   - count < 5:
        -> loop_start
    - else:
        -> loop_end
}

== loop_end ==
循环结束，共执行了 5 次
```

#### 方法 2：使用 XML 扩展格式

```md
<loop i in 0..5>
    主角：这是第 {i} 次循环
</loop>
循环结束，共执行了 5 次
```

#### 4.3.2 在循环中使用条件判断

```md
<loop i in 0..5>
    <if i == 0>
        主角：开始循环！
    <else-if i == 5>
        主角：最后一次循环！
    <else>
        主角：这是第 {i} 次循环
    </if>
</loop>
循环结束，共执行了 5 次
```

#### 4.3.3 序列循环

#### 方法 1：使用跳转和条件判断

```md
== daily_routine ==
{day = 0}

-> day_loop

== day_loop ==
{day++}

{   - day == 1:
        第一天：你来到了新学校
    - day == 2:
        第二天：你认识了新同学
    - day == 3:
        第三天：你发现了学校的秘密
    - day <= 7:
        第 {day} 天：你继续调查
    - else:
        -> end_routine
}

-> day_loop

== end_routine ==
一周的调查结束了
```

#### 方法 2：使用 XML 扩展格式

```md
<loop day in 1..7>
    <if day == 1>
        第一天：你来到了新学校
    <else-if day == 2>
        第二天：你认识了新同学
    <else-if day == 3>
        第三天：你发现了学校的秘密
    <else>
        第 {day} 天：你继续调查
    </if>
</loop>
一周的调查结束了
```

#### 4.3.4 菜单循环

#### 方法 1：使用传统格式

```md
== main_menu ==

* [查看状态]
    樱井美咲好感度：{sakura_affection}
    黑崎莲好感度：{ren_affection}
    -> main_menu

* [继续调查]
    -> investigation

* [休息]
    你休息了一会儿，恢复了精力
    -> main_menu

* [退出]
    -> game_end
```

#### 方法 2：使用 XML 扩展格式

```md
<menu id="main_menu">
    <option text="查看状态">
        樱井美咲好感度：{sakura_affection}
        黑崎莲好感度：{ren_affection}
        -> main_menu
    </option>
    <option text="继续调查">
        -> investigation
    </option>
    <option text="休息">
        你休息了一会儿，恢复了精力
        -> main_menu
    </option>
    <option text="退出">
        -> game_end
    </option>
</menu>
```

### 4.4 模块命令

支持多种模块命令：

#### 4.4.1 音频命令

```md
\audio::play("bgm/theme.mp3", 0.6)
\audio::stop()
\audio::fade_out(1.5)
\audio::play_se("se/item_receive.wav")
```

#### 4.4.2 场景命令

```md
\scene::change("bg/school.png", "fade", 1.0)
\scene::transition("slide_left", 0.8)
```

#### 4.4.3 角色命令

```md
\character::show("sakura", "smile", "left", "slide_left")
\character::hide("left", "fade_out")
\character::move("sakura", "center", "slide")
```

#### 4.4.4 特效命令

```md
\effect::flash(0.3)
\effect::shake(0.5, 10)
\effect::heart_float(1.0)
\effect::sparkle(1.5)
\effect::fade_out(2.0)
\effect::lightning(0.2)
```

### 4.5 外部函数

story 格式支持调用项目中的 Valkyrie 函数：

#### 4.5.1 直接调用外部函数

直接调用项目中的 Valkyrie 函数：

```md
\module::function()

# 示例
\game::save_game("save1.sav")
\ui::show_message("游戏已保存")
\player::heal(50)
```

#### 4.5.2 外部函数的使用场景

- **游戏系统功能**：保存/加载游戏、设置选项、管理成就
- **UI 交互**：显示消息、弹出对话框、更新界面
- **游戏逻辑**：角色状态管理、物品系统、战斗系统
- **资源管理**：加载资源、播放音效、切换场景

```md
# 示例：使用外部函数管理游戏状态
\using game::player
\using ui::dialog

== game_over ==

\player::get_status()

{   - player::is_alive():
        你还活着，可以继续游戏
    - else:
        \dialog::show("游戏结束", "你已经死亡，是否重新开始？")
        * [重新开始]
            \game::load_last_save()
            -> start
        * [退出游戏]
            \game::quit()
}
```

## 5. 文件结构

一个 story 文件通常包含以下部分：

1. **注释**：文件顶部的注释说明
2. **变量定义**：游戏中使用的变量
3. **包含语句**：包含其他章节文件
4. **场景定义**：游戏的各个场景
5. **选项分支**：玩家的选择
6. **命令调用**：音频、场景、角色、特效等命令

**示例文件结构**：

```md
# ============================================
# 星光学院的秘密 - 主线剧本
# ============================================

{player_name = "主角"}
{sakura_affection = 0}

\include chapter1_school.story
\include chapter2_investigation.story

== prologue ==

\audio::play("bgm/mystery_theme.mp3", 0.6)
\scene::change("bg/school_gate_sunset.png", "fade", 1.5)

星光学院，这所历史悠久的名校，隐藏着不为人知的秘密。

樱井美咲：新同学！你在看什么呢？

* [好啊，我去看看]
    主角：好啊，我去看看！
    {sakura_affection++}
    -> accept_student_council

* [抱歉，我还有事]
    主角：抱歉，我还有事。
    -> decline_student_council

== accept_student_council ==

樱井美咲：太好了！那放学后见！

-> after_school_choice
```

## 6. 解析和处理

GG Game Engine 使用 oak-notedown 框架解析 story 文件：

1. **词法分析**：oak-notedown lexer 将源码分解为词法单元
2. **语法分析**：构建 notedown AST
3. **命令处理**：识别命令并转换为游戏指令
4. **代码生成**：生成对话系统可执行的指令

## 7. 与其他格式的关系

- **与 notedown 的关系**：story 格式基于 notedown，完全兼容 notedown 语法
- **与脚本语言的关系**：story 格式可以与 Valkyrie 脚本结合使用，实现更复杂的游戏逻辑

## 8. 工具支持

GG Game Engine 提供以下 story 工具：

- **story 编辑器**：内置的 story 编辑器，支持语法高亮和实时预览
- **story 解析器**：基于 oak-notedown 的解析器
- **story 验证器**：验证 story 文件的语法正确性
- **story 编译器**：将 story 文件编译为优化的格式，提高运行时性能

## 9. 最佳实践

- **使用注释**：在文件开头添加注释说明
- **模块化设计**：使用 `\include` 拆分章节
- **合理使用变量**：使用变量跟踪游戏状态
- **清晰的场景结构**：使用 `== 场景名 ==` 组织剧情
- **测试和验证**：使用 story 验证器检查语法错误
- **版本控制**：将 story 文件纳入版本控制系统，跟踪变更

## 10. 示例

### 10.1 基本对话示例

```md
# 基本对话示例

{player_name = "主角"}
{sakura_affection = 0}

== start ==

\audio::play("bgm/peaceful.mp3", 0.5)
\scene::change("bg/school_gate.png", "fade", 1.0)
\character::show("sakura", "smile", "left")

樱井美咲：你好，你是新来的转学生吗？

* [是的，我是]
    主角：是的，我是新来的。
    {sakura_affection++}
    -> introduce

* [你是？]
    主角：请问你是？
    -> ask_name

== introduce ==

樱井美咲：我是樱井美咲，学生会副会长！

-> end

== ask_name ==

樱井美咲：我是樱井美咲，很高兴认识你！

-> end

== end ==

樱井美咲：再见！

\effect::fade_out(1.0)

-> DONE
```

### 10.2 高级功能示例

```md
# 高级功能示例

{has_key = false}
{clues_found = 0}

== start ==

\scene::change("bg/forest.png", "fade", 1.0)
主角进入了神秘的森林。

使用 `{ - 条件: 内容 }` 格式：
{
    - has_key == true:
        主角：我有钥匙，可以打开那个门了！
    - else:
        主角：前面有一扇门，但是我没有钥匙。
}

或者使用 XML 扩展格式：
<if has_key == true>
    主角：我有钥匙，可以打开那个门了！
<else>
    主角：前面有一扇门，但是我没有钥匙。
</if>

* [寻找钥匙]
    -> find_key

* [继续前进]
    -> continue

== find_key ==

\scene::change("bg/forest_clearing.png", "fade", 0.8)

主角：这里有一把钥匙！
{has_key = true}
{clues_found++}

-> start

== continue ==

主角：先继续前进吧，钥匙下次再说。

-> DONE
```

## 11. 语法规则总结

1. **注释**：`# 注释内容`
2. **变量定义**：`{变量名 = 初始值}`
3. **文本显示**：直接书写文本或使用 `角色名：对话` 格式
4. **场景定义**：`== 场景名 ==`
5. **选项分支**：`* [选项文本]`
6. **条件选项**：`* {条件} [选项文本]`
7. **跳转**：`-> 目标`
8. **条件判断**：`{ - 条件: 内容 }` 或 `<if 条件>内容<else>内容</if>`
9. **变量操作**：`{表达式}`（单行，等价于 `<script>表达式</script>`）或 `<script>多行表达式</script>`
10. **命令调用**：`\模块::函数(参数)`
11. **包含文件**：`\include 文件名`
12. **导入语句**：`\using 模块或函数`
13. **分隔线**：`---`
14. **原生 XML**：直接在文本中使用 XML 标签，如 `<em>斜体</em>`、`<strong>粗体</strong>`、`<ruby>漢字<rt>かんじ</rt></ruby>`
15. **循环**：`{ - 条件: 跳转 }` 或 `<loop var in start..end>内容</loop>`
16. **菜单**：`* [选项文本]` 或 `<menu id="菜单名"><option text="选项文本">内容</option></menu>`

## 12. 注意事项

- **变量名**：不能包含空格和特殊字符
- **场景名**：必须唯一
- **跳转目标**：必须存在
- **条件表达式**：必须是有效的逻辑表达式
- **命令调用**：参数必须正确
- **缩进**：使用缩进来表示代码块
- **语法一致性**：保持语法的一致性和规范性

## 13. 结论

story 格式基于 notedown，结合了 notedown 的简洁性和游戏脚本的专业性。通过 oak-notedown 框架提供的解析支持，开发者可以更高效地创建和管理游戏内容，为玩家提供更丰富的游戏体验。
