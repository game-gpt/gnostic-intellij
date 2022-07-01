package com.github.game_gpt.ide.intentions

import com.intellij.codeInsight.intention.IntentionAction
import com.intellij.codeInsight.intention.IntentionManager
import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.util.PsiUtilBase
import com.intellij.util.IncorrectOperationException

/**
 * GG 引擎生命周期函数生成意图动作
 */
class GnosticLifecycleFunctionsIntention : PsiElementBaseIntentionAction(), IntentionAction {
    /**
     * 获取实现类名称
     * @return 实现类名称
     */
    fun getImplementationClassName(): String {
        return this.javaClass.name
    }

    override fun getText(): String = "Generate GG Engine Lifecycle Functions"
    
    override fun getFamilyName(): String = "GG Engine"
    
    override fun isAvailable(project: Project, editor: Editor?, element: PsiElement): Boolean {
        val file = element.containingFile
        return file?.virtualFile?.extension in listOf("script")
    }
    
    @Throws(IncorrectOperationException::class)
    override fun invoke(project: Project, editor: Editor?, element: PsiElement) {
        if (editor == null) return
        
        val caretOffset = editor.caretModel.offset
        val document = editor.document
        
        // 生成生命周期函数代码
        val lifecycleFunctions = generateLifecycleFunctions()
        
        // 插入代码到光标位置
        document.insertString(caretOffset, lifecycleFunctions)
    }
    
    override fun startInWriteAction(): Boolean = true
    
    private fun generateLifecycleFunctions(): String {
        return """
// GG Engine Lifecycle Functions

/**
 * 初始化函数
 * 在实体创建时调用
 */
fn init() {
    // 初始化代码
}

/**
 * 每帧更新函数
 * 在每一帧调用
 * @param delta_time 帧间隔时间
 */
micro update(delta_time: f32) {
    // 更新代码
}

/**
 * 物理更新函数
 * 在物理模拟前调用
 * @param fixed_delta_time 固定时间步长
 */
micro fixed_update(fixed_delta_time: f32) {
    // 物理更新代码
}

/**
 * 渲染前函数
 * 在渲染前调用
 */
micro pre_render() {
    // 渲染前准备代码
}

/**
 * 渲染后函数
 * 在渲染后调用
 */
micro post_render() {
    // 渲染后处理代码
}

/**
 * 销毁函数
 * 在实体销毁时调用
 */
fn destroy() {
    // 清理代码
}

/**
 * 碰撞开始函数
 * 当碰撞开始时调用
 * @param other 碰撞的其他实体
 */
fn on_collision_enter(other: Entity) {
    // 碰撞处理代码
}

/**
 * 碰撞持续函数
 * 当碰撞持续时调用
 * @param other 碰撞的其他实体
 */
fn on_collision_stay(other: Entity) {
    // 碰撞持续处理代码
}

/**
 * 碰撞结束函数
 * 当碰撞结束时调用
 * @param other 碰撞的其他实体
 */
fn on_collision_exit(other: Entity) {
    // 碰撞结束处理代码
}

/**
 * 触发器进入函数
 * 当进入触发器时调用
 * @param other 进入触发器的实体
 */
fn on_trigger_enter(other: Entity) {
    // 触发器处理代码
}

/**
 * 触发器持续函数
 * 当在触发器内时调用
 * @param other 在触发器内的实体
 */
fn on_trigger_stay(other: Entity) {
    // 触发器持续处理代码
}

/**
 * 触发器退出函数
 * 当退出触发器时调用
 * @param other 退出触发器的实体
 */
fn on_trigger_exit(other: Entity) {
    // 触发器退出处理代码
}

/**
 * 鼠标按下函数
 * 当鼠标按下时调用
 * @param button 鼠标按钮
 * @param position 鼠标位置
 */
fn on_mouse_down(button: MouseButton, position: Vec2) {
    // 鼠标按下处理代码
}

/**
 * 鼠标释放函数
 * 当鼠标释放时调用
 * @param button 鼠标按钮
 * @param position 鼠标位置
 */
fn on_mouse_up(button: MouseButton, position: Vec2) {
    // 鼠标释放处理代码
}

/**
 * 鼠标移动函数
 * 当鼠标移动时调用
 * @param position 鼠标位置
 * @param delta 鼠标移动 delta
 */
fn on_mouse_move(position: Vec2, delta: Vec2) {
    // 鼠标移动处理代码
}

/**
 * 键盘按下函数
 * 当键盘按下时调用
 * @param key 按键
 */
fn on_key_down(key: KeyCode) {
    // 键盘按下处理代码
}

/**
 * 键盘释放函数
 * 当键盘释放时调用
 * @param key 按键
 */
fn on_key_up(key: KeyCode) {
    // 键盘释放处理代码
}

/**
 * 资源加载完成函数
 * 当资源加载完成时调用
 * @param asset 加载的资源
 */
fn on_asset_loaded(asset: Asset) {
    // 资源加载完成处理代码
}

/**
 * 场景加载完成函数
 * 当场景加载完成时调用
 */
fn on_scene_loaded() {
    // 场景加载完成处理代码
}

/**
 * 场景卸载函数
 * 当场景卸载时调用
 */
fn on_scene_unloaded() {
    // 场景卸载处理代码
}

/**
 * 暂停函数
 * 当游戏暂停时调用
 */
fn on_pause() {
    // 暂停处理代码
}

/**
 * 恢复函数
 * 当游戏恢复时调用
 */
fn on_resume() {
    // 恢复处理代码
}

/**
 * 应用聚焦函数
 * 当应用获得焦点时调用
 */
fn on_focus_gained() {
    // 聚焦处理代码
}

/**
 * 应用失焦函数
 * 当应用失去焦点时调用
 */
fn on_focus_lost() {
    // 失焦处理代码
}
        """.trimIndent()
    }
}
