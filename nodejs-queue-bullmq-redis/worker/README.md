
## Equality
`== undefined` or `== null` checks whether a value is either null or undefined.
```ts
let a;
let b = null
console.log(a,b);
if (a == null) {
    console.log('a is null');
}

if (a == undefined) {
    console.log('a is null');
}

if (b == null) {
    console.log('b is null');
}

if (b == undefined) {
    console.log('b is null');
}
```

## `in` operator narrowing
```ts
type Fish = { swim: () => void };
type Bird = { fly: () => void };
 
function move(animal: Fish | Bird) {
  if ("swim" in animal) {
    return animal.swim();
  }
 
  return animal.fly();
}
```

##  Exhaustiveness checking

```ts
type Shape = Circle | Square;
 
function getArea(shape: Shape) {
  switch (shape.kind) {
    case "circle":
      return Math.PI * shape.radius ** 2;
    case "square":
      return shape.sideLength ** 2;
    default:
      const _exhaustiveCheck: never = shape;
      return _exhaustiveCheck;
  }
}
```

## Optional types

```ts
function setPadding(side: 'top' | 'bottom' | 'left' | 'right', padding: number) {
  // ...
}

```

```ts
type someFunction = (a: number, b: number) => number;

type SomeType = {
    name: string;
    calculate?: someFunction;
}

const someObject: SomeType = {
    name: 'Some Object',
    calculate: (a: number, b: number) => a + b
}
if (someObject.calculate) {
    console.log(someObject.calculate(1, 2));
} else {
    console.log('No calculate function');

}
```

## Call Signatures
Something callable with properties

```ts
type DescribableFunction = {
  description: string;
  (someArg: number): boolean;
};
function doSomething(fn: DescribableFunction) {
  console.log(fn.description + " returned " + fn(6));
}

```

### Generic constraints

```ts
function longest<Type extends { length: number }>(a: Type, b: Type) {
  if (a.length >= b.length) {
    return a;
  } else {
    return b;
  }
}

// longerArray is of type 'number[]'
const longerArray = longest([1, 2], [1, 2, 3]);
// longerString is of type 'alice' | 'bob'
const longerString = longest("alice", "bob");
// Error! Numbers don't have a 'length' property
const notOK = longest(10, 100);
```