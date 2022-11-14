import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.squareup.kotlinpoet.FileSpec
import java.io.File

class SafeArgsProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger,
    private val options: Map<String, String>
) : SymbolProcessor {

    var invoked = false

    override fun process(resolver: Resolver): List<KSAnnotated> {
        if (invoked) {
            return emptyList()
        }
        invoked = true

        log("[testksp]")
        val safeArgsName = "com.ckenergy.compose.safeargs.anotation.SafeArgs"
        val symbols = resolver
            .getSymbolsWithAnnotation(safeArgsName)
            .filterIsInstance<KSClassDeclaration>()

        symbols.forEach {
            val parent = it
            val packageName = parent.containingFile!!.packageName.asString()
            val originClass = parent.simpleName.asString()
            val className = "${originClass}DestinationProvider"
            checkFile(packageName, "$className.kt")
            val file = codeGenerator.createNewFile(
                Dependencies(false, it.containingFile!!),
                packageName,
                className
            )
            var routeName =
                parent.annotations.firstOrNull { it1 ->
                    it1.annotationType.resolve().declaration.qualifiedName?.asString() == safeArgsName
                }?.arguments?.find { it1 -> it1.name?.getShortName() == "routeName" }?.value.toString()
            if (routeName.isBlank()) {
                routeName = packageName+originClass
            }
            file.bufferedWriter().use { writer ->
                try {
                    val builder = StringBuilder()
                    val classCreate = ClassCreatorProxy(builder)
                    val typeSpec = classCreate.generateJavaCode(
                        parent.asType(emptyList()),
                        className, routeName
                    )
                    val fileSpec = FileSpec.get(packageName, typeSpec)
                    fileSpec.writeTo(writer)
                } catch (e: Exception) {
                    log(e.stackTraceToString())
                }
            }
//            file.close()
            log("[testksp] className:$packageName.$className")
        }

        return emptyList()
    }

    private fun checkFile(packageName: String, fileName: String) {
        val file = File(packageName.replace(".", "/"), fileName)
        if (file.exists()) {
            file.delete()
        }
    }

    private fun log(msg: String) {
        logger.warn("SafeArgsProcessor: $msg")
    }

}