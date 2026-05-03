export const BRANCH_API_BASE = "http://localhost:8080/api/ChiNhanh";

export async function readErrorMessage(response) {
  const fallback = "Co loi xay ra. Vui long thu lai.";
  const contentType = response.headers.get("content-type") || "";

  try {
    if (contentType.includes("application/json")) {
      const data = await response.json();
      return data.message || data.error || fallback;
    }

    const text = await response.text();
    return text || fallback;
  } catch {
    return fallback;
  }
}

export async function fetchBranchJson(url, options = {}) {
  const response = await fetch(url, options);

  if (!response.ok) {
    throw new Error(await readErrorMessage(response));
  }

  if (response.status === 204) {
    return null;
  }

  return response.json();
}
