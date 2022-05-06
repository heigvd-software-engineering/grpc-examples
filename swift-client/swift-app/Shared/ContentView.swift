//
//  ContentView.swift
//  Shared
//
//  Created by James Smith on 06.05.22.
//

import SwiftUI

struct ContentView: View {
    @State private var username: String = ""
    @State private var message: String = ""
    
    var body: some View {
        VStack {
            Text("Hello, world!")
                .padding()
            
            Form {
                TextField(text: $username, prompt: Text("Required")) {
                    Text("Username")
                }
            }
            
            Button(action: {
                message = "Hello \(username). Welcome to the world"
            }) {
                Text("Send")
                    .padding()
            }
            
            Text(message)
                .padding()
            
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        Group {
            ContentView()
        }
    }
}
