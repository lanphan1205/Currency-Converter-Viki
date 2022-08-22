## Design
### App Design Pattern
This project uses Model-View-ViewModel (MVVM) design pattern with a strong emphasis on single responsibiltiy principle
* Unlike Model-View-Controller (MVC) where View depends on both Controller and Model, View only depends on ViewModel in MVVM, hence more flexible to code changes
* Unlike Model-View-Presenter (MVP) design pattern where Presenter and View knows about each other, View knows about ViewModel but ViewModel has no information about View in MVVM
### View Design
1. UI
* We use Edit Text for the amount fields and Spinner for the list of Currencies. User actions that trigger updates of To field include:
    * Text changes in From field
    * Item selected in From Spinner
    * Item selected in To Spinner
* This minimizes required actions from user and provide a real-time experience for user
2. Functionalities
* The single responsibilty of View is to receive user actions and display data. It *observes* the *observables* in ViewModel for any data updates. Any business logic implementation is hidden from View
### Model Design
* This is the data for business logic of the app. It provides logic like currency conversion and used by ViewModel to provide data to View
* The design uses encapsulation to restrict access to class members
### ViewModel Design
* Provides data to View as *observables*
* Uses Repository class to provide network operations
* Uses LiveData, a cycle-aware observable, for better life cycle management
### Service
* Repository class responsible for networking operations
* Usage of Coroutine to improve app responsiveness


## Further Improvement
### Handling network operation
* Need to detect if device is connected to the internet
* Need to handle other response types other than 200
* Need to display error message for user
### Unit Testing
* Because this is production codes, we need to provide unit tests
* MVVM design pattern allows hassle-free unit testing


## Feedback
* The assignment is sufficient for me to revise my knowledge on Android development. Have a lot of fun doing it!