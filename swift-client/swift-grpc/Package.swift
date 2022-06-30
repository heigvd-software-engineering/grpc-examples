// swift-tools-version:5.2

import PackageDescription
// swiftformat puts the next import before the tools version.
// swiftformat:disable:next sortedImports
import class Foundation.ProcessInfo

let grpcPackageName = "swift-grpc"
let grpcProductName = "GRPC"
let cgrpcZlibProductName = "CGRPCZlib"
let grpcTargetName = grpcProductName
let cgrpcZlibTargetName = cgrpcZlibProductName

let includeNIOSSL = ProcessInfo.processInfo.environment["GRPC_NO_NIO_SSL"] == nil

// MARK: - Package Dependencies

let packageDependencies: [Package.Dependency] = [
    .package(
        url: "https://github.com/apple/swift-nio.git",
        from: "2.32.0"
    ),
    .package(
        url: "https://github.com/apple/swift-nio-http2.git",
        from: "1.19.2"
    ),
    .package(
        url: "https://github.com/apple/swift-nio-transport-services.git",
        from: "1.11.1"
    ),
    .package(
        url: "https://github.com/apple/swift-nio-extras.git",
        from: "1.4.0"
    ),
    .package(
        name: "SwiftProtobuf",
        url: "https://github.com/apple/swift-protobuf.git",
        from: "1.9.0"
    ),
    .package(
        url: "https://github.com/apple/swift-log.git",
        from: "1.4.0"
    ),
].appending(
    .package(
        url: "https://github.com/apple/swift-nio-ssl.git",
        from: "2.14.0"
    ),
    if: includeNIOSSL
)

// MARK: - Target Dependencies

extension Target.Dependency {
    // Target dependencies; external
    static let grpc: Self = .target(name: grpcTargetName)
    static let cgrpcZlib: Self = .target(name: cgrpcZlibTargetName)
    static let protocGenGRPCSwift: Self = .target(name: "protoc-gen-grpc-swift")
    
    // Target dependencies; internal
    static let helloWorldModel: Self = .target(name: "HelloWorldModel")
    static let helloWorldClient: Self = .target(name: "HelloWorldClient")
    
    // Product dependencies
    static let nio: Self = .product(name: "NIO", package: "swift-nio")
    static let nioConcurrencyHelpers: Self = .product(
        name: "NIOConcurrencyHelpers",
        package: "swift-nio"
    )
    static let nioCore: Self = .product(name: "NIOCore", package: "swift-nio")
    static let nioEmbedded: Self = .product(name: "NIOEmbedded", package: "swift-nio")
    static let nioExtras: Self = .product(name: "NIOExtras", package: "swift-nio-extras")
    static let nioFoundationCompat: Self = .product(name: "NIOFoundationCompat", package: "swift-nio")
    static let nioHTTP1: Self = .product(name: "NIOHTTP1", package: "swift-nio")
    static let nioHTTP2: Self = .product(name: "NIOHTTP2", package: "swift-nio-http2")
    static let nioPosix: Self = .product(name: "NIOPosix", package: "swift-nio")
    static let nioSSL: Self = .product(name: "NIOSSL", package: "swift-nio-ssl")
    static let nioTLS: Self = .product(name: "NIOTLS", package: "swift-nio")
    static let nioTransportServices: Self = .product(
        name: "NIOTransportServices",
        package: "swift-nio-transport-services"
    )
    static let logging: Self = .product(name: "Logging", package: "swift-log")
    static let protobuf: Self = .product(name: "SwiftProtobuf", package: "SwiftProtobuf")
    static let protobufPluginLibrary: Self = .product(
        name: "SwiftProtobufPluginLibrary",
        package: "SwiftProtobuf"
    )
}

// MARK: - Targets

extension Target {
    static let grpc: Target = .target(
        name: grpcTargetName,
        dependencies: [
            .cgrpcZlib,
            .nio,
            .nioCore,
            .nioPosix,
            .nioEmbedded,
            .nioFoundationCompat,
            .nioTLS,
            .nioTransportServices,
            .nioHTTP1,
            .nioHTTP2,
            .nioExtras,
            .logging,
            .protobuf,
        ].appending(
            .nioSSL, if: includeNIOSSL
        ),
        path: "Sources/GRPC"
    )
    
    static let cgrpcZlib: Target = .target(
        name: cgrpcZlibTargetName,
        path: "Sources/CGRPCZlib",
        linkerSettings: [
            .linkedLibrary("z"),
        ]
    )
    
    static let protocGenGRPCSwift: Target = .target(
        name: "protoc-gen-grpc-swift",
        dependencies: [
            .protobuf,
            .protobufPluginLibrary,
        ]
    )
    
    
    static let helloWorldModel: Target = .target(
        name: "HelloWorldModel",
        dependencies: [
            .grpc,
            .nio,
            .protobuf,
        ],
        path: "Sources/HelloWorld/Model"
    )
    
    static let helloWorldClient: Target = .target(
        name: "HelloWorldClient",
        dependencies: [
            .grpc,
            .helloWorldModel,
            .nioCore,
            .nioPosix,
        ],
        path: "Sources/HelloWorld/Client"
    )
}

// MARK: - Products

extension Product {
    static let grpc: Product = .library(
        name: grpcProductName,
        targets: [grpcTargetName]
    )
    
    static let cgrpcZlib: Product = .library(
        name: cgrpcZlibProductName,
        targets: [cgrpcZlibTargetName]
    )
    static let helloWorldClient: Product = .library(name: "HelloWorldClient", targets: ["HelloWorldClient"])
    
    static let protocGenGRPCSwift: Product = .executable(
        name: "protoc-gen-grpc-swift",
        targets: ["protoc-gen-grpc-swift"]
    )
}

// MARK: - Package

let package = Package(
    name: grpcPackageName,
    products: [
        .grpc,
        .cgrpcZlib,
        .protocGenGRPCSwift,
        .helloWorldClient
    ],
    dependencies: packageDependencies,
    targets: [
        // Products
        .grpc,
        .cgrpcZlib,
        .protocGenGRPCSwift,
        .helloWorldClient,
        .helloWorldModel,
    ]
)

extension Array {
    func appending(_ element: Element, if condition: Bool) -> [Element] {
        if condition {
            return self + [element]
        } else {
            return self
        }
    }
}