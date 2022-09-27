// This file can be replaced during build by using the `fileReplacements` array.
// `ng build` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  firebaseConfig : {
    apiKey: 'AIzaSyBDmAmPQ4oZ2KlXtp1qhk8HDndGzHTAyLo',
    authDomain: 'sprint1-c2e64.firebaseapp.com',
    databaseURL: 'https://sprint1-c2e64-default-rtdb.firebaseio.com',
    projectId: 'sprint1-c2e64',
    storageBucket: 'sprint1-c2e64.appspot.com',
    messagingSenderId: '715730046220',
    appId: '1:715730046220:web:e4e0e868fca6888dc2cd29',
    measurementId: 'G-2DDMRCPRQW'
  },
  apiBaseUrl: 'http://localhost:8080'
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/plugins/zone-error';  // Included with Angular CLI.
