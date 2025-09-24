document.getElementById('contactForm').addEventListener('submit', async (e) => {
  e.preventDefault();
  const form = e.target;
  const data = {
    name: form.name.value,
    email: form.email.value,
    message: form.message.value
  };
  const response = await fetch('/contact', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(data)
  });
  const result = await response.text();
  document.getElementById('status').innerText = result;
});
