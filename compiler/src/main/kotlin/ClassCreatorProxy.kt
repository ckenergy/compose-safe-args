import com.google.devtools.ksp.symbol.KSType
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.ksp.toClassName

/**
 * ckenergy  2022-11-11
 */
class ClassCreatorProxy(val logBuilder: StringBuilder) {

    private val provider =
        ClassName.bestGuess("com.ckenergy.compose.safeargs.service.IDestinationProvider")
    private val providerImpl =
        ClassName.bestGuess("com.ckenergy.compose.safeargs.service.DestinationProviderImpl")

    /**
     * 创建Java代码
     *
     * @return
     */
    fun generateJavaCode(
        destinationName: KSType,
        className: String,
        routeName: String
    ): TypeSpec {
        val delegateName = "provider"
        val provider1 = provider.parameterizedBy(destinationName.toClassName())
        val bindingClass = TypeSpec.classBuilder(className)
            .addModifiers(KModifier.PUBLIC)
            .addSuperinterface(
                provider1,
                CodeBlock.of(delegateName)
            )

        val parameterSpec = ParameterSpec.builder(delegateName, provider1)
            .defaultValue(
                "%T(\"$routeName\",%T::class.java)",
                providerImpl,
                destinationName.toClassName()
            )
            .build()
        val flux = FunSpec.constructorBuilder()
            .addParameter(parameterSpec)
            .build()
        bindingClass.primaryConstructor(flux)

        return bindingClass.build()
    }

    private fun log(msg: String) {
        logBuilder.append(msg + "\n")
    }

}