#GitHuB Demo App by Matheus Villela

###Modules

```flow
au=>operation: android-ui
pr=>operation: presentation
da=>operation: data
au->pr->da
```

**android-ui**: is the only module that depends on android. In the module there are all the DI entrypoints and config, and the android components - Fragments and a main Activity

**presentation**: as this project uses a MVVM pattern this module has the ViewModels and classes that holds the ViewModels states

**data**: holds Repositories, in the project context our API calls

The way of the modules are organized follows the Clean Architecture principles about dependencies.
There are no UseCases to avoid having a proxy layer, in the context of this project they would be no more than that, in most apps it would be needed.
The presentation module, separated from ui one, is to make clear that this layer should not be plataform dependent. *presentation* and *data* layer could very well be kotlin-native modules shared across diferent plataforms.


### Scoped DI
The project uses a dynamic scoping dependency injection library, JSR 330 compilant, [ToothPick](https://github.com/stephanenicolas/toothpick). A 3 line changed version of androidx fragment lib is also used to unlock the full potential of a scoped DI approach.
One of the greatest benefits of the approach used is to have 100% constructor injection DI and no service locator pattern used outside initialization code.
Another one is that, by having Toothpick, there is no need to write trivial factories. The preprocessor will generate the factories of the classes that we annotate and only in some scenarios like OkHttpClient writing a Factory(Provider) is needed.

### RxJava
Due to the choice of having a plataform independent presentation module it was used BehaviourSubject/Observable instead of AAC LiveData. As a BehaviourSubject can have a default value, unlike LiveData, the exposed Observables in the ViewModels offers a more clear interface for de plataform dependent views.
Since RxJava is the choice for the ViewModels state it was also used for Repositories asynchronous calls.
Coroutines with Flowables could be a good alternative over RxJava without changing the core ideas used in the project, and also making it easier to have our non plataform dependent modules kotlin-native.
