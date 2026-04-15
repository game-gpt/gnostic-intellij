# GG 项目 2026年4月工作安排

## 项目概览

GG 是一个元引擎架构的游戏引擎项目，当前 MVP 目标是跑通两套 UI 体系并正常启动无特化引擎。

### MVP 目标

1. **Editor UI 体系**：ui uber shader + *.widget
2. **Game UI 体系**：*.shader + *.prefab
3. **无特化引擎启动**：gg-engine 正常启动运行

---

# gg-engine 核心组（MVP 关键路径）

- **负责模块**：gg-engine
- **大致进度**：15%（存根实现）
- **优先级**：🔴 **最高优先级** - MVP 关键路径
- **本月工作重点**：
  - 实现 Engine::run() 主循环（帧率控制、事件处理）
  - 实现 init_window() 窗口初始化（Winit 集成）
  - 集成 WgpuRenderer 渲染后端
  - 集成 ScriptEngine 脚本引擎
  - 实现 AssetServer 资源加载流程
- **长期目标**：
  - 完整的无特化引擎实现
  - 支持插件组合生成特化引擎
  - 支持游戏 UI 体系（*.shader + *.prefab）
- **相关文件**：
  - gg-engine: `projects/engines/gg-engine/src/engine.rs`
  - gg-engine: `projects/engines/gg-engine/src/lib.rs`

---

# Editor UI 组（MVP 关键路径）

- **负责模块**：gg-editor-ui, gg-editor-render
- **大致进度**：gg-editor-ui 75%，gg-editor-render 90%
- **优先级**：🔴 **最高优先级** - MVP 关键路径
- **本月工作重点**：
  - 完善 Uber-Shader 渲染管线
  - 实现 *.widget 文件加载和渲染
  - 添加缺失的 UI 组件（下拉菜单、对话框、工具提示）
  - 优化文本渲染性能
  - 完善 Widget 生命周期管理
- **长期目标**：
  - 完整的 Editor UI 组件库
  - 高性能渲染管线
  - 支持 *.widget 文件热重载
- **相关文件**：
  - gg-editor-ui: `projects/editor/gg-editor-ui/src/lib.rs`
  - gg-editor-render: `projects/editor/gg-editor-render/src/lib.rs`

---

# Shader 编译组（MVP 关键路径）

- **负责模块**：gg-compiler-shader
- **大致进度**：65%
- **优先级**：🟡 **高优先级** - Game UI 体系依赖
- **本月工作重点**：
  - 完善 Shader 变体系统
  - 实现 WGSL 输出（WGPU 兼容）
  - 完善 lower 模块（类型转换、语义映射）
  - 优化 Shader 编译性能
  - 支持 Shader 热重载
- **长期目标**：
  - 完整的 Shader 语言支持
  - SPIR-V 输出（跨平台编译）
  - 可视化 Shader 编辑器支持
- **相关文件**：
  - gg-compiler-shader: `projects/compiler/gg-compiler-shader/src/compiler.rs`

---

# Widget 编译组（MVP 关键路径）

- **负责模块**：gg-compiler-widget
- **大致进度**：80%
- **优先级**：🟡 **高优先级** - Editor UI 体系依赖
- **本月工作重点**：
  - 完善 oak-voc AST 接收接口
  - 实现 Template 完整语义分析
  - 完善 SCSS + Tailwind CSS 子集处理
  - 支持组件导入导出
  - 实现响应式状态管理
- **长期目标**：
  - 完整的 Widget 编译系统
  - 响应式状态管理
  - 组件热重载
- **相关文件**：
  - gg-compiler-widget: `projects/compiler/gg-compiler-widget/src/lib.rs`

---

# Inspector 完善组

- **负责模块**：gg-editor-inspector
- **大致进度**：60%（原计划 75%，高估 15%）
- **优先级**：🟢 **中优先级** - 编辑器核心功能
- **本月工作重点**：
  - 实现数值滑块控件
  - 实现颜色选择器
  - 实现枚举下拉选择
  - 实现资源选择器
  - 完善 UI 控件交互逻辑
- **长期目标**：
  - 完整的属性编辑器
  - 支持自定义属性编辑器
  - 支持复杂数据类型编辑
- **相关文件**：
  - gg-editor-inspector: `projects/editor/gg-editor-inspector/src/lib.rs`

---

# Scene 编辑组

- **负责模块**：gg-editor-scene
- **大致进度**：65%（原计划 80%，高估 15%）
- **优先级**：🟢 **中优先级** - 编辑器核心功能
- **本月工作重点**：
  - 实现变换工具交互逻辑（移动/旋转/缩放）
  - 完善框选功能
  - 实现拖拽资源创建实体
  - 集成撤销/重做命令系统
  - 完善场景导航
- **长期目标**：
  - 完整的场景编辑器
  - 支持多选和批量操作
  - 支持 Gizmo 自定义
- **相关文件**：
  - gg-editor-scene: `projects/editor/gg-editor-scene/src/lib.rs`

---

# Runtime 核心组

- **负责模块**：gg-runtime, gg-vm
- **大致进度**：gg-runtime 85%，gg-vm 90%
- **优先级**：🟢 **中优先级** - 支撑引擎运行
- **本月工作重点**：
  - 完善 HMR 热更新机制
  - 优化游戏循环性能
  - 完善调试协议支持
  - 优化脚本执行性能
  - 完善错误处理和诊断
- **长期目标**：
  - 完善的运行时基础设施
  - 高性能脚本执行
  - 完整的调试支持
- **相关文件**：
  - gg-runtime: `projects/runtime/gg-runtime/src/lib.rs`
  - gg-vm: `projects/runtime/gg-vm/src/lib.rs`

---

# 渲染后端组

- **负责模块**：gg-render-wgpu, gg-render-html
- **大致进度**：gg-render-wgpu 75%，gg-render-html 60%
- **优先级**：🟢 **中优先级** - 支撑渲染功能
- **本月工作重点**：
  - 完善 WGPU 渲染管线
  - 优化精灵批渲染性能
  - 完善文本渲染（SDF 字体）
  - 支持 *.prefab 渲染
  - 实现渲染性能分析工具
- **长期目标**：
  - 高性能跨平台渲染
  - 完整的 2D 渲染能力
  - 支持自定义着色器
- **相关文件**：
  - gg-render-wgpu: `projects/runtime/gg-render-wgpu/src/lib.rs`
  - gg-render-html: `projects/runtime/gg-render-html/src/lib.rs`

---

# 资产管辖组

- **负责模块**：gg-compiler-asset, gg-meta, gg-schema
- **大致进度**：gg-compiler-asset 50%，gg-meta 80%，gg-schema 55%
- **优先级**：🟢 **中优先级** - 支撑资源管理
- **本月工作重点**：
  - 完善资产注册表和依赖图（循环依赖检测、依赖更新）
  - 实现增量构建系统（基于文件哈希、依赖变更检测）
  - 支持所有资产格式（Animation、Config、Material、Prefab、Scene、VON、Schema、Script、Shader、Widget、Meta）
  - 优化资产加载性能（异步加载、优先级调度）
  - 完善资产缓存策略（内存缓存、磁盘缓存、缓存失效）
- **长期目标**：
  - 完整的资产管辖系统
  - 支持所有资产格式
  - 高效的增量编译
  - 完善的依赖管理
- **相关文件**：
  - gg-compiler-asset: `projects/compiler/gg-compiler-asset/src/lib.rs`
  - gg-meta: `projects/core/gg-meta/src/lib.rs`
  - gg-schema: `projects/compiler/gg-schema/src/lib.rs`

---

## 关键瓶颈和风险点

### 🔴 高风险

| 瓶颈 | 影响 | 建议措施 |
|------|------|---------|
| gg-engine 存根实现 | MVP 无法启动 | 优先实现主循环和渲染集成 |
| Inspector UI 控件缺失 | 编辑器不可用 | 实现基础控件（滑块、颜色选择器） |
| Scene 变换工具缺失 | 场景编辑不可用 | 实现拖拽交互逻辑 |

### 🟡 中风险

| 瓶颈 | 影响 | 建议措施 |
|------|------|---------|
| Shader 编译器不完善 | Game UI 渲染受限 | 完善 WGSL 输出 |
| Widget 编译器不完善 | Editor UI 渲染受限 | 完善语义分析 |

---

## 里程碑规划

### Phase 1: MVP 基础（Week 1-2）

- [ ] 实现 gg-engine 主循环
- [ ] 实现 gg-engine 窗口初始化
- [ ] 集成 WgpuRenderer
- [ ] 完善 Uber-Shader 渲染

### Phase 2: MVP 完善（Week 3-4）

- [ ] 实现 *.widget 文件加载
- [ ] 实现 *.shader 文件编译
- [ ] 实现 *.prefab 渲染
- [ ] gg-engine 正常启动验证

### Phase 3: 编辑器完善（Week 5-8）

- [ ] 实现 Inspector 基础控件
- [ ] 实现 Scene 变换工具
- [ ] 完善编辑器 UI 组件
- [ ] 完善资产管辖系统

---

## 发布标准

- gg-engine 能够正常启动并运行
- Editor UI 体系能够加载和渲染 *.widget 文件
- Game UI 体系能够加载和渲染 *.prefab 文件
- 所有测试通过
- 性能达到 60fps
