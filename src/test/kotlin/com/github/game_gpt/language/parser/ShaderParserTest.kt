package com.github.game_gpt.language.parser

import com.github.game_gpt.ide.definitions.ShaderParserDefinition
import com.intellij.testFramework.ParsingTestCase
import java.util.concurrent.TimeUnit

class ShaderParserTest : ParsingTestCase("parser/shader", "shader", false, ShaderParserDefinition()) {

    override fun getTestDataPath(): String {
        return "src/test/testData"
    }

    fun testSample() {
        val startTime = System.currentTimeMillis()
        val timeout = TimeUnit.SECONDS.toMillis(30)
        
        doTest(true, true)
        
        val elapsed = System.currentTimeMillis() - startTime
        if (elapsed > timeout) {
            fail("Test timed out after $elapsed milliseconds")
        }
    }

    fun testComputeFunction() {
        doTest("compute/ComputeFunction")
    }

    fun testFallbackBlock() {
        doTest("fallback/FallbackBlock")
    }

    fun testFragmentFunction() {
        doTest("fragment/FragmentFunction")
    }

    fun testShaderDeclaration() {
        doTest("shader/ShaderDeclaration")
    }

    fun testShaderProperty() {
        doTest("property/ShaderProperty")
    }

    fun testRenderStates() {
        doTest("render_states/RenderStates")
    }

    fun testUniformsBlock() {
        doTest("uniforms/UniformsBlock")
    }

    fun testVertexFunction() {
        doTest("vertex/VertexFunction")
    }
}