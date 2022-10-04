package main


import (
    "fmt"
    "github.com/gofiber/fiber/v2"
 )


func main() {
    app := fiber.New()

    app.Get("/", func(c *fiber.Ctx) error {
        return c.SendString("asd,  👋!")
    })

    app.Get("/item/ping", func(c *fiber.Ctx) error {
        return c.JSON(map[string]string{"hello": "world"})
    })

    fmt.Println("Hello, World!2")


    app.Listen(":3000")
}