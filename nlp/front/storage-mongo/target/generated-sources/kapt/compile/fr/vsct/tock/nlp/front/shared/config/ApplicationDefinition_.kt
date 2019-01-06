package fr.vsct.tock.nlp.front.shared.config

import fr.vsct.tock.nlp.core.NlpEngineType
import java.util.Locale
import kotlin.Boolean
import kotlin.String
import kotlin.Suppress
import kotlin.collections.Collection
import kotlin.collections.Map
import kotlin.collections.Set
import kotlin.reflect.KProperty1
import org.litote.kmongo.Id
import org.litote.kmongo.property.KCollectionPropertyPath
import org.litote.kmongo.property.KCollectionSimplePropertyPath
import org.litote.kmongo.property.KMapPropertyPath
import org.litote.kmongo.property.KMapSimplePropertyPath
import org.litote.kmongo.property.KPropertyPath

private val __Name: KProperty1<ApplicationDefinition, String?>
    get() = ApplicationDefinition::name
private val __Namespace: KProperty1<ApplicationDefinition, String?>
    get() = ApplicationDefinition::namespace
private val __Intents: KProperty1<ApplicationDefinition, Set<Id<IntentDefinition>>?>
    get() = ApplicationDefinition::intents
private val __SupportedLocales: KProperty1<ApplicationDefinition, Set<Locale>?>
    get() = ApplicationDefinition::supportedLocales
private val __IntentStatesMap: KProperty1<ApplicationDefinition, Map<Id<IntentDefinition>,
        Set<String>>?>
    get() = ApplicationDefinition::intentStatesMap
private val __NlpEngineType: KProperty1<ApplicationDefinition, NlpEngineType?>
    get() = ApplicationDefinition::nlpEngineType
private val __MergeEngineTypes: KProperty1<ApplicationDefinition, Boolean?>
    get() = ApplicationDefinition::mergeEngineTypes
private val __UseEntityModels: KProperty1<ApplicationDefinition, Boolean?>
    get() = ApplicationDefinition::useEntityModels
private val __SupportSubEntities: KProperty1<ApplicationDefinition, Boolean?>
    get() = ApplicationDefinition::supportSubEntities
private val ___id: KProperty1<ApplicationDefinition, Id<ApplicationDefinition>?>
    get() = ApplicationDefinition::_id
class ApplicationDefinition_<T>(previous: KPropertyPath<T, *>?, property: KProperty1<*,
        ApplicationDefinition?>) : KPropertyPath<T, ApplicationDefinition?>(previous,property) {
    val name_: KPropertyPath<T, String?>
        get() = KPropertyPath<T, String?>(this,__Name)

    val namespace: KPropertyPath<T, String?>
        get() = KPropertyPath<T, String?>(this,__Namespace)

    val intents: KCollectionSimplePropertyPath<T, Id<IntentDefinition>?>
        get() = KCollectionSimplePropertyPath<T,
                Id<IntentDefinition>?>(this,ApplicationDefinition::intents)

    val supportedLocales: KCollectionSimplePropertyPath<T, Locale?>
        get() = KCollectionSimplePropertyPath<T,
                Locale?>(this,ApplicationDefinition::supportedLocales)

    val intentStatesMap: KMapSimplePropertyPath<T, Id<IntentDefinition>?, Set<String>?>
        get() = KMapSimplePropertyPath<T, Id<IntentDefinition>?,
                Set<String>?>(this,ApplicationDefinition::intentStatesMap)

    val nlpEngineType: KPropertyPath<T, NlpEngineType?>
        get() = KPropertyPath<T, NlpEngineType?>(this,__NlpEngineType)

    val mergeEngineTypes: KPropertyPath<T, Boolean?>
        get() = KPropertyPath<T, Boolean?>(this,__MergeEngineTypes)

    val useEntityModels: KPropertyPath<T, Boolean?>
        get() = KPropertyPath<T, Boolean?>(this,__UseEntityModels)

    val supportSubEntities: KPropertyPath<T, Boolean?>
        get() = KPropertyPath<T, Boolean?>(this,__SupportSubEntities)

    val _id: KPropertyPath<T, Id<ApplicationDefinition>?>
        get() = KPropertyPath<T, Id<ApplicationDefinition>?>(this,___id)

    companion object {
        val Name: KProperty1<ApplicationDefinition, String?>
            get() = __Name
        val Namespace: KProperty1<ApplicationDefinition, String?>
            get() = __Namespace
        val Intents: KCollectionSimplePropertyPath<ApplicationDefinition, Id<IntentDefinition>?>
            get() = KCollectionSimplePropertyPath(null, __Intents)
        val SupportedLocales: KCollectionSimplePropertyPath<ApplicationDefinition, Locale?>
            get() = KCollectionSimplePropertyPath(null, __SupportedLocales)
        val IntentStatesMap: KMapSimplePropertyPath<ApplicationDefinition, Id<IntentDefinition>?,
                Set<String>?>
            get() = KMapSimplePropertyPath(null, __IntentStatesMap)
        val NlpEngineType: KProperty1<ApplicationDefinition, NlpEngineType?>
            get() = __NlpEngineType
        val MergeEngineTypes: KProperty1<ApplicationDefinition, Boolean?>
            get() = __MergeEngineTypes
        val UseEntityModels: KProperty1<ApplicationDefinition, Boolean?>
            get() = __UseEntityModels
        val SupportSubEntities: KProperty1<ApplicationDefinition, Boolean?>
            get() = __SupportSubEntities
        val _id: KProperty1<ApplicationDefinition, Id<ApplicationDefinition>?>
            get() = ___id}
}

class ApplicationDefinition_Col<T>(previous: KPropertyPath<T, *>?, property: KProperty1<*,
        Collection<ApplicationDefinition>?>) : KCollectionPropertyPath<T, ApplicationDefinition?,
        ApplicationDefinition_<T>>(previous,property) {
    val name_: KPropertyPath<T, String?>
        get() = KPropertyPath<T, String?>(this,__Name)

    val namespace: KPropertyPath<T, String?>
        get() = KPropertyPath<T, String?>(this,__Namespace)

    val intents: KCollectionSimplePropertyPath<T, Id<IntentDefinition>?>
        get() = KCollectionSimplePropertyPath<T,
                Id<IntentDefinition>?>(this,ApplicationDefinition::intents)

    val supportedLocales: KCollectionSimplePropertyPath<T, Locale?>
        get() = KCollectionSimplePropertyPath<T,
                Locale?>(this,ApplicationDefinition::supportedLocales)

    val intentStatesMap: KMapSimplePropertyPath<T, Id<IntentDefinition>?, Set<String>?>
        get() = KMapSimplePropertyPath<T, Id<IntentDefinition>?,
                Set<String>?>(this,ApplicationDefinition::intentStatesMap)

    val nlpEngineType: KPropertyPath<T, NlpEngineType?>
        get() = KPropertyPath<T, NlpEngineType?>(this,__NlpEngineType)

    val mergeEngineTypes: KPropertyPath<T, Boolean?>
        get() = KPropertyPath<T, Boolean?>(this,__MergeEngineTypes)

    val useEntityModels: KPropertyPath<T, Boolean?>
        get() = KPropertyPath<T, Boolean?>(this,__UseEntityModels)

    val supportSubEntities: KPropertyPath<T, Boolean?>
        get() = KPropertyPath<T, Boolean?>(this,__SupportSubEntities)

    val _id: KPropertyPath<T, Id<ApplicationDefinition>?>
        get() = KPropertyPath<T, Id<ApplicationDefinition>?>(this,___id)

    @Suppress("UNCHECKED_CAST")
    override fun memberWithAdditionalPath(additionalPath: String): ApplicationDefinition_<T> =
            ApplicationDefinition_(this, customProperty(this, additionalPath))}

class ApplicationDefinition_Map<T, K>(previous: KPropertyPath<T, *>?, property: KProperty1<*, Map<K,
        ApplicationDefinition>?>) : KMapPropertyPath<T, K, ApplicationDefinition?,
        ApplicationDefinition_<T>>(previous,property) {
    val name_: KPropertyPath<T, String?>
        get() = KPropertyPath<T, String?>(this,__Name)

    val namespace: KPropertyPath<T, String?>
        get() = KPropertyPath<T, String?>(this,__Namespace)

    val intents: KCollectionSimplePropertyPath<T, Id<IntentDefinition>?>
        get() = KCollectionSimplePropertyPath<T,
                Id<IntentDefinition>?>(this,ApplicationDefinition::intents)

    val supportedLocales: KCollectionSimplePropertyPath<T, Locale?>
        get() = KCollectionSimplePropertyPath<T,
                Locale?>(this,ApplicationDefinition::supportedLocales)

    val intentStatesMap: KMapSimplePropertyPath<T, Id<IntentDefinition>?, Set<String>?>
        get() = KMapSimplePropertyPath<T, Id<IntentDefinition>?,
                Set<String>?>(this,ApplicationDefinition::intentStatesMap)

    val nlpEngineType: KPropertyPath<T, NlpEngineType?>
        get() = KPropertyPath<T, NlpEngineType?>(this,__NlpEngineType)

    val mergeEngineTypes: KPropertyPath<T, Boolean?>
        get() = KPropertyPath<T, Boolean?>(this,__MergeEngineTypes)

    val useEntityModels: KPropertyPath<T, Boolean?>
        get() = KPropertyPath<T, Boolean?>(this,__UseEntityModels)

    val supportSubEntities: KPropertyPath<T, Boolean?>
        get() = KPropertyPath<T, Boolean?>(this,__SupportSubEntities)

    val _id: KPropertyPath<T, Id<ApplicationDefinition>?>
        get() = KPropertyPath<T, Id<ApplicationDefinition>?>(this,___id)

    @Suppress("UNCHECKED_CAST")
    override fun memberWithAdditionalPath(additionalPath: String): ApplicationDefinition_<T> =
            ApplicationDefinition_(this, customProperty(this, additionalPath))}
