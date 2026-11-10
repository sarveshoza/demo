const form = document.querySelector("#greeting-form");
const nameInput = document.querySelector("#name");
const submitButton = form.querySelector("button");
const response = document.querySelector("#response");
const responseMessage = document.querySelector("#response-message");
const installButton = document.querySelector("#install-button");
let installPrompt;

nameInput.value = localStorage.getItem("hello-name") || "";

form.addEventListener("submit", async (event) => {
  event.preventDefault();
  const name = nameInput.value.trim();
  if (!name) {
    nameInput.focus();
    return;
  }

  localStorage.setItem("hello-name", name);
  submitButton.disabled = true;
  submitButton.querySelector("span").textContent = "Sending a little hello…";
  response.hidden = true;

  try {
    const url = new URL("/hello", window.location.origin);
    url.searchParams.set("name", name);
    const result = await fetch(url);
    if (!result.ok) throw new Error(`Greeting request failed (${result.status})`);
    const message = await result.text();
    responseMessage.textContent = message || `Hello, ${name}!`;
  } catch {
    responseMessage.textContent = `Hello, ${name}! Your greeting is ready on this device.`;
  } finally {
    response.hidden = false;
    submitButton.disabled = false;
    submitButton.querySelector("span").textContent = "Send a little hello";
  }
});

window.addEventListener("beforeinstallprompt", (event) => {
  event.preventDefault();
  installPrompt = event;
  installButton.hidden = false;
});

installButton.addEventListener("click", async () => {
  if (!installPrompt) return;
  installPrompt.prompt();
  await installPrompt.userChoice;
  installPrompt = undefined;
  installButton.hidden = true;
});

window.addEventListener("appinstalled", () => {
  installPrompt = undefined;
  installButton.hidden = true;
});

if ("serviceWorker" in navigator) {
  window.addEventListener("load", () => {
    navigator.serviceWorker.register("/service-worker.js").catch((error) => {
      console.error("Could not register the offline app shell.", error);
    });
  });
}
