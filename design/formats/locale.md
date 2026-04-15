# Locale 格式规范

## 1. 格式概述

Locale 文件（`.locale`）是 GG 游戏引擎用于存储多语言翻译数据的格式。采用基于 Fluent 的语法，结合 Markdown 风格，提供强大的本地化功能，同时与 Valkyrie 体系完全兼容。

## 2. 设计目标

- **简洁易读**：基于 Fluent 语法，保持简洁易读特点
- **功能强大**：支持复数、性别、变量等高级翻译功能
- **易于解析**：语法规则清晰，便于解析和处理
- **与 Valkyrie 兼容**：使用与 Valkyrie 一致的变量语法
- **与 story 格式一致**：保持与 Galgame 格式的风格一致性

## 3. 核心语法

### 3.1 注释

使用 `#` 开头的行作为注释：

```
# 这是注释内容
# 可以在任何地方使用
```

### 3.2 基本消息

使用 `message-id = message` 格式定义基本消息：

```
welcome = 欢迎来到星光学院！
game-title = 星光学院的秘密
```

### 3.3 变量插值

支持在消息中使用变量，使用与 Valkyrie 一致的语法：

```
greet-by-name = 你好，{name}！
item-found = 你找到了 {item}！
player-status = 等级：{level}，生命值：{hp}/{max_hp}
```

### 3.4 复数形式

使用 `{ count -> ... }` 语法处理复数：

```
item-count = {
    count ->
        [one] 你有 {count} 个物品
       *[other] 你有 {count} 个物品
}

apple-count = {
    count ->
        [one] 你有 {count} 个苹果
       *[other] 你有 {count} 个苹果
}
```

### 3.5 性别选择器

使用 `{ gender -> ... }` 语法处理性别：

```
welcome-user = {
    gender ->
        [male] 欢迎，先生！
        [female] 欢迎，女士！
       *[other] 欢迎！
}
```

### 3.6 多语言支持

使用统合式结构，每个消息包含所有语言的翻译：

```
welcome = {
    zh-CN = 欢迎来到星光学院！
    en-US = Welcome to Star Academy!
    ja-JP = 星光学院へようこそ！
}

greet-by-name = {
    zh-CN = 你好，{name}！
    en-US = Hello, {name}!
    ja-JP = こんにちは、{name}！
}
```

### 3.7 消息引用

支持引用其他消息：

```
welcome = {
    zh-CN = 欢迎来到游戏！
    en-US = Welcome to the game!
    ja-JP = ゲームへようこそ！
}

start-game = {
    zh-CN = {welcome} 点击开始按钮开始游戏。
    en-US = {welcome} Click the start button to begin.
    ja-JP = {welcome} スタートボタンをクリックしてゲームを開始します。
}
```

### 3.8 术语

支持定义和使用术语：

```
# 术语定义
-term star-academy = {
    zh-CN = 星光学院
    en-US = Star Academy
    ja-JP = 星光学院
}

# 使用术语
welcome = {
    zh-CN = 欢迎来到 {star-academy}！
    en-US = Welcome to {star-academy}!
    ja-JP = {star-academy}へようこそ！
}

description = {
    zh-CN = {star-academy} 是一所历史悠久的名校。
    en-US = {star-academy} is a historic school.
    ja-JP = {star-academy}は歴史ある名校です。
}
```

### 3.9 注释和元数据

支持为消息添加注释和元数据：

```
# 角色：樱井美咲
# 语气：友好活泼
sakura-greeting = {
    zh-CN = 新同学！你在看什么呢？
    en-US = New student! What are you looking at?
    ja-JP = 新入生さん！何を見ているの？
}

# 角色：黑崎莲
# 语气：酷酷的
ren-invite = {
    zh-CN = 喂，新来的。要不要跟我去个地方？
    en-US = Hey, new kid. Wanna come with me somewhere?
    ja-JP = おい、新入り。一緒にどこか行かないか？
}
```

## 4. 高级功能

### 4.1 复杂选择器

支持多个变量的复杂选择器：

```
greet-player = {
    gender ->
        [male] {
            age ->
                [child] {
                    zh-CN = 你好，小弟弟！
                    en-US = Hello, little brother!
                    ja-JP = こんにちは、弟くん！
                }
                [teen] {
                    zh-CN = 你好，小伙子！
                    en-US = Hello, young man!
                    ja-JP = こんにちは、若者！
                }
               *[adult] {
                    zh-CN = 你好，先生！
                    en-US = Hello, sir!
                    ja-JP = こんにちは、紳士！
                }
        }
        [female] {
            age ->
                [child] {
                    zh-CN = 你好，小妹妹！
                    en-US = Hello, little sister!
                    ja-JP = こんにちは、妹ちゃん！
                }
                [teen] {
                    zh-CN = 你好，小姑娘！
                    en-US = Hello, young lady!
                    ja-JP = こんにちは、お嬢さん！
                }
               *[adult] {
                    zh-CN = 你好，女士！
                    en-US = Hello, ma'am!
                    ja-JP = こんにちは、淑女！
                }
        }
       *[other] {
            zh-CN = 你好！
            en-US = Hello!
            ja-JP = こんにちは！
        }
}
```

### 4.2 嵌套消息

支持嵌套消息定义：

```
ui = {
    button = {
        ok = {
            zh-CN = 确定
            en-US = OK
            ja-JP = 確認
        }
        cancel = {
            zh-CN = 取消
            en-US = Cancel
            ja-JP = キャンセル
        }
    }
    dialog = {
        title = {
            zh-CN = 提示
            en-US = Notice
            ja-JP = 通知
        }
        confirm = {
            zh-CN = 你确定吗？
            en-US = Are you sure?
            ja-JP = 本当によろしいですか？
        }
    }
}
```

### 4.3 条件表达式

支持简单的条件表达式：

```
access-message = {
    has-access ->
        [true] {
            zh-CN = 你可以进入
            en-US = You can enter
            ja-JP = 入ることができます
        }
       *[false] {
            zh-CN = 你没有权限进入
            en-US = You don't have permission to enter
            ja-JP = 入る権限がありません
        }
}
```

### 4.4 日期和时间格式化

支持日期和时间格式化：

```
last-login = {
    zh-CN = 上次登录：{date | date}
    en-US = Last login: {date | date}
    ja-JP = 最終ログイン：{date | date}
}

current-time = {
    zh-CN = 当前时间：{time | time}
    en-US = Current time: {time | time}
    ja-JP = 現在の時間：{time | time}
}
```

### 4.5 数字格式化

支持数字格式化：

```
score = {
    zh-CN = 得分：{score | number}
    en-US = Score: {score | number}
    ja-JP = スコア：{score | number}
}

percentage = {
    zh-CN = 完成度：{percent | number(style: "percent")}
    en-US = Completion: {percent | number(style: "percent")}
    ja-JP = 完成率：{percent | number(style: "percent")}
}
```

## 5. 文件结构

一个 Locale 文件通常包含以下部分：

1. **注释**：文件顶部的注释说明
2. **术语定义**：可重用的术语
3. **消息定义**：具体的翻译消息

**示例文件结构**：

```
# ============================================
# 星光学院的秘密 - 翻译文件
# ============================================

# 术语定义
-term star-academy = {
    zh-CN = 星光学院
    en-US = Star Academy
    ja-JP = 星光学院
}

-term student-council = {
    zh-CN = 学生会
    en-US = Student Council
    ja-JP = 生徒会
}

# 基本消息
welcome = {
    zh-CN = 欢迎来到 {star-academy}！
    en-US = Welcome to {star-academy}!
    ja-JP = {star-academy}へようこそ！
}

game-title = {
    zh-CN = 星光学院的秘密
    en-US = Secrets of Star Academy
    ja-JP = 星光学院の秘密
}

# 角色对话
sakura-greeting = {
    zh-CN = 新同学！你在看什么呢？
    en-US = New student! What are you looking at?
    ja-JP = 新入生さん！何を見ているの？
}

ren-invite = {
    zh-CN = 喂，新来的。要不要跟我去个地方？
    en-US = Hey, new kid. Wanna come with me somewhere?
    ja-JP = おい、新入り。一緒にどこか行かないか？
}

# 选项文本
choice-accept = {
    zh-CN = 好啊，我去看看
    en-US = Sure, I'll check it out
    ja-JP = うん、見てみるよ
}

choice-decline = {
    zh-CN = 抱歉，我还有事
    en-US = Sorry, I have other plans
    ja-JP = ごめん、用事があるんだ
}

# 复数形式
item-count = {
    count ->
        [one] {
            zh-CN = 你有 {count} 个物品
            en-US = You have {count} item
            ja-JP = アイテムを {count} 個持っています
        }
       *[other] {
            zh-CN = 你有 {count} 个物品
            en-US = You have {count} items
            ja-JP = アイテムを {count} 個持っています
        }
}
```

## 6. 语言代码

使用 BCP 47 语言标签：

| 代码 | 语言 |
|-----|------|
| zh-CN | 简体中文 |
| zh-TW | 繁体中文 |
| en-US | 美式英语 |
| en-GB | 英式英语 |
| ja-JP | 日语 |
| ko-KR | 韩语 |

## 7. 元信息存储

元信息（源语言、支持语言、版本、统计、作者等）存储在对应的 `.locale.meta` 文件中，详见 [meta.md](./meta.md)。

## 8. 文件扩展名

Locale 文件使用 `.locale` 扩展名：

```
main.locale
main.locale.meta
chapter1_school.locale
chapter1_school.locale.meta
```

## 9. Fluent 特性借鉴

### 9.1 核心 Fluent 特性

1. **消息 ID 与消息分离**：清晰区分消息标识符和翻译内容
2. **强大的选择器系统**：支持复数、性别、条件等选择器
3. **变量插值**：灵活的变量替换机制
4. **消息引用**：支持引用其他消息，减少重复
5. **术语系统**：定义可重用的术语
6. **注释支持**：丰富的注释功能，便于翻译理解
7. **本地化友好**：针对不同语言的语法特点优化

### 9.2 语法优势

- **简洁明了**：语法简洁，易于阅读和编写
- **表达力强**：支持复杂的本地化场景
- **错误容忍**：即使语法有小错误也能优雅降级
- **工具友好**：易于开发工具支持
- **Valkyrie 兼容**：使用与 Valkyrie 一致的变量语法

## 10. 最佳实践

1. **使用术语**：对重复出现的术语使用术语定义
2. **合理组织**：按功能或模块组织消息
3. **添加注释**：为复杂消息添加注释说明
4. **使用选择器**：正确使用复数和性别选择器
5. **测试验证**：测试翻译在游戏中的显示效果
6. **版本控制**：将 locale 和 meta 文件都纳入版本控制

## 11. 完整示例

```
# ============================================
# 星光学院的秘密 - 主翻译文件
# ============================================

# 术语定义
-term star-academy = {
    zh-CN = 星光学院
    en-US = Star Academy
    ja-JP = 星光学院
}

-term old-building = {
    zh-CN = 旧校舍
    en-US = Old Building
    ja-JP = 旧校舎
}

-term student-council = {
    zh-CN = 学生会
    en-US = Student Council
    ja-JP = 生徒会
}

# 基本叙述
prologue-narration = {
    zh-CN = {star-academy}，这所历史悠久的名校，隐藏着不为人知的秘密。
    en-US = {star-academy}, a historic school, hides secrets unknown to all.
    ja-JP = {star-academy}、この歴史ある名校は、人知れぬ秘密を秘めている。
}

# 角色对话
sakura-hello = {
    zh-CN = 新同学！你在看什么呢？
    en-US = New student! What are you looking at?
    ja-JP = 新入生さん！何を見ているの？
}

ren-invite = {
    zh-CN = 喂，新来的。要不要跟我去个地方？
    en-US = Hey, new kid. Wanna come with me somewhere?
    ja-JP = おい、新入り。一緒にどこか行かないか？
}

yuki-greet = {
    zh-CN = 啊，新同学也来了。欢迎欢迎。
    en-US = Oh, the new student is here too. Welcome!
    ja-JP = あ、新入生さんも来たのね。ようこそ。
}

# 选项文本
choice-accept = {
    zh-CN = 好啊，我去看看
    en-US = Sure, I'll check it out
    ja-JP = うん、見てみるよ
}

choice-decline = {
    zh-CN = 抱歉，我还有事
    en-US = Sorry, I have other plans
    ja-JP = ごめん、用事があるんだ
}

choice-ask = {
    zh-CN = {student-council}有什么活动？
    en-US = What's the {student-council} activity?
    ja-JP = {student-council}の活動は何？
}

# 复数形式
clue-count = {
    count ->
        [one] {
            zh-CN = 你发现了 {count} 条线索
            en-US = You found {count} clue
            ja-JP = 手がかりを {count} つ見つけた
        }
       *[other] {
            zh-CN = 你发现了 {count} 条线索
            en-US = You found {count} clues
            ja-JP = 手がかりを {count} つ見つけた
        }
}

# 性别选择器
greet-user = {
    gender ->
        [male] {
            zh-CN = 你好，同学！
            en-US = Hello, student!
            ja-JP = こんにちは、生徒！
        }
        [female] {
            zh-CN = 你好，同学！
            en-US = Hello, student!
            ja-JP = こんにちは、生徒！
        }
       *[other] {
            zh-CN = 你好！
            en-US = Hello!
            ja-JP = こんにちは！
        }
}

# 系统消息
system-save = {
    zh-CN = 游戏已保存
    en-US = Game saved
    ja-JP = ゲームを保存しました
}

system-load = {
    zh-CN = 游戏已加载
    en-US = Game loaded
    ja-JP = ゲームを読み込みました
}
```

## 12. 解析和处理

Galgame 引擎解析 Locale 文件的过程：

1. **词法分析**：将源码分解为词法单元
2. **语法分析**：构建 Fluent 风格的 AST
3. **消息处理**：解析每个消息的结构和选择器
4. **代码生成**：生成翻译系统可使用的数据结构

## 13. 与其他格式的关系

- **与 story 格式的关系**：保持风格一致，使用相同的注释语法
- **与 Valkyrie 的关系**：使用与 Valkyrie 一致的变量语法
- **与 Fluent 的关系**：基于 Fluent 设计，适配游戏本地化需求
- **与 JSON 的关系**：更易读，支持注释，更适合人工编辑
- **与 PO/POT 的关系**：功能更强大，语法更现代

## 14. 工具支持

Galgame 引擎提供以下 Locale 工具：

- **Locale 编辑器**：内置的 Locale 编辑器，支持语法高亮和实时预览
- **Locale 解析器**：解析 Locale 文件
- **Locale 验证器**：验证 Locale 文件的语法正确性
- **Locale 编译器**：将 Locale 文件编译为优化的格式
- **Locale 提取工具**：从 story 文件中提取需要翻译的文本
- **Locale 比较工具**：比较不同语言版本的翻译

## 15. 语法规则总结

1. **注释**：`# 注释内容`
2. **基本消息**：`message-id = { zh-CN = ..., en-US = ... }`
3. **变量插值**：`{variable}`
4. **复数形式**：`{ count -> [one] { ... } *[other] { ... } }`
5. **性别选择器**：`{ gender -> [male] { ... } [female] { ... } *[other] { ... } }`
6. **术语定义**：`-term term-id = { zh-CN = ..., en-US = ... }`
7. **消息引用**：`{message-id}`
8. **嵌套消息**：`parent = { child = { ... } }`

## 16. 注意事项

- **消息 ID**：使用小写字母、连字符和数字，避免空格和特殊字符
- **语言代码**：使用 BCP 47 语言标签
- **变量名**：与 Valkyrie 和 story 格式保持一致
- **选择器**：使用 `*` 标记默认选项
- **缩进**：使用缩进来提高可读性
- **语法一致性**：保持语法的一致性和规范性

## 结论

Locale 格式基于 Fluent 的优秀设计，结合了 Markdown 的简洁性和游戏本地化的专业性。通过强大的选择器系统、变量插值、消息引用等特性，同时保持与 Valkyrie 体系的兼容性，开发者可以更高效地管理多语言翻译，为玩家提供更好的游戏体验。
